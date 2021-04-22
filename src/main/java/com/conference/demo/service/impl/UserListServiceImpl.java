package com.conference.demo.service.impl;

import com.conference.demo.entities.User;
import com.conference.demo.repository.UserRepository;
import com.conference.demo.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserListServiceImpl implements UserListService {
    private final UserRepository userRepository;

    @Autowired
    public UserListServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public int deleteUserById(long id) {
        return userRepository.deleteUserById(id);
    }
}
