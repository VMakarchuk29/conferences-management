package com.conference.demo.service.impl;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.dto.TopicOfReportDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.entities.ReportTopic;
import com.conference.demo.entities.User;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.repository.UserRepository;
import com.conference.demo.service.CreateConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreateConferenceServiceImpl implements CreateConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;

    @Autowired
    public CreateConferenceServiceImpl(ConferenceRepository conferenceRepository, UserRepository userRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Conference createConference(CreateConferenceDTO dto) {
        List<ReportTopic> reportTopics = new ArrayList<>();
        for (TopicOfReportDTO topicOfReport : dto.getTopicOfReports()) {
            Optional<User> speaker = userRepository.findById(topicOfReport.getSpeakerId());

            ReportTopic topic = buildReportTopic(topicOfReport);
            reportTopics.add(topic);

            speaker.ifPresent(user -> user.getSpeakersReports().add(topic));
        }
        Conference conference = buildConference(dto);

        reportTopics.forEach(topic -> topic.setConference(conference));

        conference.setTopicOfReports(reportTopics);
        return conferenceRepository.save(conference);
    }

    private Conference buildConference(CreateConferenceDTO dto) {
        return Conference.builder()
                .topic(dto.getTopic())
                .timeOfHolding(LocalDateTime.of(dto.getDate(), dto.getTime()))
                .numberOfParticipants(dto.getNumberOfParticipants())
                .venue(dto.getVenue())
                .build();
    }

    private ReportTopic buildReportTopic(TopicOfReportDTO topicOfReport) {
        return ReportTopic.builder()
                .topic(topicOfReport.getTopic())
                .build();
    }
}
