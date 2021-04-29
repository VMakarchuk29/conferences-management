package com.conference.demo.service;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.exception.ConferenceNotFoundException;

public interface EditConferenceService {
    CreateConferenceDTO convertConferenceToDTO(Conference conference);

    Conference updateById(long id, CreateConferenceDTO dto) throws ConferenceNotFoundException;
}
