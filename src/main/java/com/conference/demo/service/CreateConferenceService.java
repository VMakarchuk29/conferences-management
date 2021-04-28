package com.conference.demo.service;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.entities.Conference;

public interface CreateConferenceService {
    Conference createConference(CreateConferenceDTO dto);
}
