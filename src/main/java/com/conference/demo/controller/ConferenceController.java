package com.conference.demo.controller;

import com.conference.demo.entities.Conference;
import com.conference.demo.exception.PageNotFound;
import com.conference.demo.service.ConferenceService;
import com.conference.demo.util.Pagination;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/conferences", "/"})
public class ConferenceController {
    private static final Logger log = Logger.getLogger(ConferenceController.class);
    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping
    public String showAllConferences(Model model, Pageable pageable) {
        String url = "/conferences";
        try {
            addAttributes(model, conferenceService.findAll(pageable), url);
        } catch (PageNotFound pageNotFound) {
            log.error(pageNotFound.getMessage());
            return String.format("redirect:%s?page=1", url);
        }
        return "index";
    }

    @GetMapping("/upcoming-events")
    public String showUpcomingConferences(Model model, Pageable pageable) {
        String url = "/conferences/upcoming-events";
        try {
            addAttributes(model, conferenceService.findAllUpcomingConference(pageable), url);
        } catch (PageNotFound pageNotFound) {
            log.error(pageNotFound.getMessage());
            return String.format("redirect:%s?page=1", url);
        }
        return "index";
    }

    @GetMapping("/past-events")
    public String showPastConferences(Model model, Pageable pageable) {
        String url = "/conferences/past-events";
        try {
            addAttributes(model, conferenceService.findAllPastConference(pageable), url);
        } catch (PageNotFound pageNotFound) {
            log.error(pageNotFound.getMessage());
            return String.format("redirect:%s?page=1", url);
        }
        return "index";
    }

    private void addAttributes(Model model, Page<Conference> pages, String url) {
        model.addAttribute("url", url);
        model.addAttribute("conferences", pages);
        model.addAttribute("start", Pagination.getStartPage(pages));
        model.addAttribute("last", Pagination.getLastPage(pages));
    }
}
