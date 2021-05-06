package com.conference.demo.service.impl;

import com.conference.demo.entities.Conference;
import com.conference.demo.entities.User;
import com.conference.demo.exception.AlreadyRegisteredException;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.repository.UserRepository;
import com.conference.demo.service.ConfRegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfRegistrationServiceImpl implements ConfRegistrationService {
    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;

    public ConfRegistrationServiceImpl(UserRepository userRepository, ConferenceRepository conferenceRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    @Transactional
    public User registerUser(String email, long conferenceId) throws AlreadyRegisteredException {
        User user = userRepository.findByEmail(email).orElseThrow();
        Conference conference = conferenceRepository.findById(conferenceId).orElseThrow();

        List<Conference> selectedConferences = user.getSelectedConferences();

        if (selectedConferences.contains(conference)) {
            throw new AlreadyRegisteredException(String.format("User '%s' already registered on the conference '%s'", email, conference.getTopic()));
        }

        selectedConferences.add(conference);

        return userRepository.save(user);
    }
}
