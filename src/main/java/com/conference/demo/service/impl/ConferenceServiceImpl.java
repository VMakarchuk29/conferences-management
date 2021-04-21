package com.conference.demo.service.impl;

import com.conference.demo.entities.Conference;
import com.conference.demo.exception.PageNotFound;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceRepository conferenceRepository;

    @Autowired
    public ConferenceServiceImpl(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    public Page<Conference> findAll(Pageable pageable) throws PageNotFound {
        return checkIfPageExist(pageable, conferenceRepository.findAll(pageable));
    }

    @Override
    public Page<Conference> findAllUpcomingConference(Pageable pageable) throws PageNotFound {
        Page<Conference> pages = conferenceRepository.findAllByTimeOfHoldingAfter(LocalDateTime.now(), pageable);
        return checkIfPageExist(pageable, pages);
    }

    @Override
    public Page<Conference> findAllPastConference(Pageable pageable) throws PageNotFound {
        Page<Conference> pages = conferenceRepository.findAllByTimeOfHoldingBefore(LocalDateTime.now(), pageable);
        return checkIfPageExist(pageable, pages);
    }

    private Page<Conference> checkIfPageExist(Pageable pageable, Page<Conference> pages) throws PageNotFound {
        if (isPageNotExist(pageable.getPageNumber(), pages)) {
            throw new PageNotFound("Conferences pages don't have this page: " + (pageable.getPageNumber() + 1));
        }
        return pages;
    }

    private boolean isPageNotExist(int pageNumber, Page<Conference> pages) {
        return pages.getTotalPages() != 0 && pages.getTotalPages() <= pageNumber;
    }
}
