package com.conference.demo.service;

import com.conference.demo.entities.Conference;
import com.conference.demo.exception.PageNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConferenceService {
    List<Conference> findAll();

    Page<Conference> findAll(Pageable pageable) throws PageNotFoundException;

    Page<Conference> findAllUpcomingConference(Pageable pageable) throws PageNotFoundException;

    Page<Conference> findAllPastConference(Pageable pageable) throws PageNotFoundException;
}
