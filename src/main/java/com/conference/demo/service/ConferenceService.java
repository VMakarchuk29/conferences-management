package com.conference.demo.service;

import com.conference.demo.entities.Conference;

import java.util.List;

public interface ConferenceService {
    List<Conference> findAll();
}
