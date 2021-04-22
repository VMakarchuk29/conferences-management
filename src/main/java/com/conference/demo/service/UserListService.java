package com.conference.demo.service;

import com.conference.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserListService {
    Page<User> findAllUsers(Pageable pageable);

    int deleteUserById(long id);
}
