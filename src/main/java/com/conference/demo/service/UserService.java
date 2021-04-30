package com.conference.demo.service;

import com.conference.demo.dto.UserRegistrationDTO;
import com.conference.demo.entities.User;
import com.conference.demo.exception.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(User user);

    Optional<User> findByEmail(String email);

    User registerNewUserAccount(UserRegistrationDTO user) throws UserAlreadyExistException;

    List<User> findAllSpeaker();
}
