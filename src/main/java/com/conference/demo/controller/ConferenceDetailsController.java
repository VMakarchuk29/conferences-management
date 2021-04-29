package com.conference.demo.controller;

import com.conference.demo.entities.Conference;
import com.conference.demo.exception.ConferenceNotFoundException;
import com.conference.demo.service.ConferenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conference-details")
public class ConferenceDetailsController {
    private static final Logger log = Logger.getLogger(ConferenceDetailsController.class);
    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceDetailsController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable("id") long id, Model model) {
        Conference conference;
        try {
            conference = conferenceService.findById(id);
        } catch (ConferenceNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute("found", true);
            return "conference-details";
        }
        model.addAttribute("found", false);
        model.addAttribute("conference", conference);

        return "conference-details";
    }
}
