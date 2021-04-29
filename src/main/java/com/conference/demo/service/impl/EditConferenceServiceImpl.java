package com.conference.demo.service.impl;

import com.conference.demo.dto.CreateConferenceDTO;
import com.conference.demo.dto.TopicOfReportDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.entities.ReportTopic;
import com.conference.demo.exception.ConferenceNotFoundException;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.service.EditConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EditConferenceServiceImpl implements EditConferenceService {
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public EditConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public CreateConferenceDTO convertConferenceToDTO(Conference conference) {
        return CreateConferenceDTO.builder()
                .topic(conference.getTopic())
                .topicOfReports(convertTopicToDTO(conference.getTopicOfReports()))
                .date(conference.getTimeOfHolding().toLocalDate())
                .time(conference.getTimeOfHolding().toLocalTime())
                .numberOfParticipants(conference.getNumberOfParticipants())
                .venue(conference.getVenue())
                .build();
    }

    @Override
    @Transactional
    public Conference updateById(long id, CreateConferenceDTO dto) throws ConferenceNotFoundException {
        Conference conference = conferenceRepository.findById(id).orElseThrow(
                () -> new ConferenceNotFoundException("Not found conference with id = " + id));

        return conferenceRepository.save(updateConference(conference, dto));
    }

    private Conference updateConference(Conference conference, CreateConferenceDTO dto) {
        return Conference.builder()
                .id(conference.getId())
                .topic(dto.getTopic())
                .topicOfReports(updateReportTopicList(dto))
                .timeOfHolding(LocalDateTime.of(dto.getDate(), dto.getTime()))
                .numberOfParticipants(dto.getNumberOfParticipants())
                .venue(dto.getVenue())
                .build();
    }

    private List<ReportTopic> updateReportTopicList(CreateConferenceDTO dto) {
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

    private List<TopicOfReportDTO> convertTopicToDTO(List<ReportTopic> topicOfReports) {
        List<TopicOfReportDTO> result = new ArrayList<>();
        for (ReportTopic reportTopic : topicOfReports) {

            result.add(TopicOfReportDTO.builder()
                    .topic(reportTopic.getTopic())
                    .build());
        }
        return result;
    }
}
