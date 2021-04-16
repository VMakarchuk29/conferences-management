package com.conference.demo.service;

import com.conference.demo.dto.UserRegistrationDTO;
import com.conference.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User user);

    User findByEmail(String email);

    User save(UserRegistrationDTO user);
}
