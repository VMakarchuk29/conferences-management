package com.conference.demo.controller;

import com.conference.demo.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {
    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping
    public String showConferences(Model model) {
        model.addAttribute("conferences", conferenceService.findAll()); //TODO return something if null
        return "index";
    }
}
