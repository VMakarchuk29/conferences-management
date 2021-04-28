package com.conference.demo.service.impl;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.dto.TopicOfReportDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.entities.ReportTopic;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.service.CreateConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                .topicOfReports(buildReportTopicList(dto))
                .build();
    }

    private Collection<ReportTopic> buildReportTopicList(CreateConferenceDTO dto) {
        List<ReportTopic> result = new ArrayList<>();
        for (TopicOfReportDTO topicOfReport : dto.getTopicOfReports()) {
            result.add(
                    ReportTopic.builder()
                            .topic(topicOfReport.getTopic())
                            .build()
            );
        }

        return result;
    }
}
