package com.conference.demo.service.impl;

import com.conference.demo.entities.Conference;
import com.conference.demo.exception.PageNotFound;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Page<Conference> pages = conferenceRepository.findAll(pageable);
        if (pages.getTotalPages() != 0 && pages.getTotalPages() <= pageable.getPageNumber()) {
            throw new PageNotFound("Conferences pages don't have this page: " + (pageable.getPageNumber() + 1));
        }
        return pages;
    }

    @Override
    public int getStartPage(Page<Conference> pages) {
        int start = Math.max(1, pages.getNumber());
        if (pages.getNumber() == pages.getTotalPages() - 1 && pages.getNumber() > 1)
            start--;
        return start;
    }

    @Override
    public int getLastPage(Page<Conference> pages) {
        int start = Math.max(1, pages.getNumber());
        return Math.min(start + 2, pages.getTotalPages());
    }
}
