package com.conference.demo.service.impl;

import com.conference.demo.dto.UserRegistrationDTO;
import com.conference.demo.entities.Role;
import com.conference.demo.entities.User;
import com.conference.demo.entities.UserInfo;
import com.conference.demo.repository.UserRepository;
import com.conference.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserRegistrationDTO userDTO) {
        User user = createUser(userDTO);
        UserInfo userInfo = createUserInfo(userDTO);

        userInfo.setUser(user);
        user.setUserInfo(userInfo);
        return userRepository.save(user);
    }

    private UserInfo createUserInfo(UserRegistrationDTO dto) {
        return UserInfo.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthday(LocalDate.parse(dto.getBirthday()))
                .phoneNumber(dto.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .gender(dto.getGender())
                .build();
    }

    private User createUser(UserRegistrationDTO dto) {
        return User.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
