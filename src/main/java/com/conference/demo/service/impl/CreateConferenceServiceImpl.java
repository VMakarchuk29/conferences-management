package com.conference.demo.service.impl;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.entities.ReportTopic;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.service.CreateConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateConferenceServiceImpl implements CreateConferenceService {
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public CreateConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public Conference createConference(CreateConferenceDTO dto) {
        return conferenceRepository.save(buildConference(dto));
    }

    private Conference buildConference(CreateConferenceDTO dto) {
        return Conference.builder()
                .topic(dto.getTopic())
                .timeOfHolding(LocalDateTime.of(dto.getDate(), dto.getTime()))
                .numberOfParticipants(dto.getNumberOfParticipants())
                .venue(dto.getVenue())
                .topicOfReport(buildReportTopic(dto))
                .build();
    }

    private ReportTopic buildReportTopic(CreateConferenceDTO dto) {
        return ReportTopic.builder()
                .topic(dto.getTopicOfReports())
                .build();
    }
}
