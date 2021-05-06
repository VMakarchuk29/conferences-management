package com.conference.demo.service.impl;

import com.conference.demo.entities.ReportTopic;
import com.conference.demo.repository.ReportTopicRepository;
import com.conference.demo.service.ReportTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportTopicServiceImpl implements ReportTopicService {
    private final ReportTopicRepository reportTopicRepository;

    @Autowired
    public ReportTopicServiceImpl(ReportTopicRepository reportTopicRepository) {
        this.reportTopicRepository = reportTopicRepository;
    }

    @Override
    public List<ReportTopic> findAllReportWithoutSpeaker(long conferenceId) {
        return reportTopicRepository.findAllByConferenceIdAndSpeakerIsNull(conferenceId);
    }
}
