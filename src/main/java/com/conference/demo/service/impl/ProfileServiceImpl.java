package com.conference.demo.service.impl;

import com.conference.demo.dto.ProfileDTO;
import com.conference.demo.entities.User;
import com.conference.demo.entities.UserInfo;
import com.conference.demo.repository.UserRepository;
import com.conference.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ProfileDTO buildProfileDTO(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();//todo
        return ProfileDTO.builder()
                .firstName(user.getUserInfo().getFirstName())
                .lastName(user.getUserInfo().getLastName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .birthday(user.getUserInfo().getBirthday())
                .phoneNumber(user.getUserInfo().getPhoneNumber())
                .filename(user.getUserInfo().getFilename())
                .gender(user.getUserInfo().getGender().name()).build();
    }

    @Override
    public void updateAccount(String email, ProfileDTO profileDto, MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            profileDto.setFilename(resultFilename);
        } else if (profileDto.getFilename() == null) {
            profileDto.setFilename("profile.png");
        }
        userRepository.save(updateUserFromDto(userRepository.findByEmail(email).orElseThrow(), profileDto));
    }

    private User updateUserFromDto(User user, ProfileDTO profileDto) {
        UserInfo userInfo = user.getUserInfo();
        userInfo.setFirstName(profileDto.getFirstName());
        userInfo.setLastName(profileDto.getLastName());
        userInfo.setBirthday(profileDto.getBirthday());
        userInfo.setPhoneNumber(profileDto.getPhoneNumber());
//        userInfo.setGender(profileDto.getGender());todo
        userInfo.setFilename(profileDto.getFilename());

        user.setUserInfo(userInfo);
        user.setUserName(profileDto.getUserName());
        user.setEmail(profileDto.getEmail());
        return user;
    }
}
