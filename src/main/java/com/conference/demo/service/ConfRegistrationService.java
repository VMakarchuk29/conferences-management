package com.conference.demo.service;

import com.conference.demo.entities.User;
import com.conference.demo.exception.AlreadyRegisteredException;

public interface ConfRegistrationService {

    User registerUser(String email, long conferenceId) throws AlreadyRegisteredException;
}
