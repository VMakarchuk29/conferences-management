package com.conference.demo.controller;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.dto.TopicOfferDTO;
import com.conference.demo.entities.Conference;
import com.conference.demo.exception.ConferenceNotFoundException;
import com.conference.demo.service.ConferenceService;
import com.conference.demo.service.ReportTopicService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conference-details")
public class ConferenceDetailsController {
    private static final Logger log = Logger.getLogger(ConferenceDetailsController.class);
    private final ConferenceService conferenceService;
    private final ReportTopicService reportTopicService;

    @Autowired
    public ConferenceDetailsController(ConferenceService conferenceService, ReportTopicService reportTopicService) {
        this.conferenceService = conferenceService;
        this.reportTopicService = reportTopicService;
    }

    @ModelAttribute
    private SpeakerOfferDTO getSpeakerOfferDTO() {
        return new SpeakerOfferDTO();
    }

    @ModelAttribute
    private TopicOfferDTO getTopicOfferDTO() {
        return new TopicOfferDTO();
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
        model.addAttribute("topicWithoutSpeaker", reportTopicService.findAllReportWithoutSpeaker(id));

        return "conference-details";
    }
}
