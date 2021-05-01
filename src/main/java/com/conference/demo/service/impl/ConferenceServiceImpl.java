package com.conference.demo.service.impl;

import com.conference.demo.dto.DateRangeDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.exception.ConferenceNotFoundException;
import com.conference.demo.exception.PageNotFoundException;
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
    public Page<Conference> findAll(Pageable pageable) throws PageNotFoundException {
        return checkIfPageExist(pageable, conferenceRepository.findAll(pageable));
    }

    @Override
    public Page<Conference> findAllUpcomingConference(Pageable pageable) throws PageNotFoundException {
        Page<Conference> pages = conferenceRepository.findAllByTimeOfHoldingAfter(LocalDateTime.now(), pageable);
        return checkIfPageExist(pageable, pages);
    }

    @Override
    public Page<Conference> findAllPastConference(Pageable pageable) throws PageNotFoundException {
        Page<Conference> pages = conferenceRepository.findAllByTimeOfHoldingBefore(LocalDateTime.now(), pageable);
        return checkIfPageExist(pageable, pages);
    }

    @Override
    public Conference findById(long id) throws ConferenceNotFoundException {
        return conferenceRepository.findById(id).orElseThrow(() -> new ConferenceNotFoundException("Not found conference with id = " + id));
    }

    @Override
    public Page<Conference> findByDateBetween(DateRangeDTO range, Pageable pageable) {
        return conferenceRepository.findByTimeOfHoldingBetween(range.getFrom().atStartOfDay(), range.getTo().atStartOfDay(), pageable);
    }

    private Page<Conference> checkIfPageExist(Pageable pageable, Page<Conference> pages) throws PageNotFoundException {
        if (isPageNotExist(pageable.getPageNumber(), pages)) {
            throw new PageNotFoundException("Conferences pages don't have this page: " + (pageable.getPageNumber() + 1));
        }
        return pages;
    }

    private boolean isPageNotExist(int pageNumber, Page<Conference> pages) {
        return pages.getTotalPages() != 0 && pages.getTotalPages() <= pageNumber;
    }
}
