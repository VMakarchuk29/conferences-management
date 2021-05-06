package com.conference.demo.service;

import com.conference.demo.entities.ReportTopic;

import java.util.List;

public interface ReportTopicService {
    List<ReportTopic> findAllReportWithoutSpeaker(long conferenceId);
}
