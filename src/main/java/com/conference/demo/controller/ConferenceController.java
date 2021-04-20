package com.conference.demo.controller;

import com.conference.demo.entities.Conference;
import com.conference.demo.exception.PageNotFound;
import com.conference.demo.service.ConferenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {
    private static final Logger log = Logger.getLogger(ConferenceController.class);
    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping
    public String showConferences(Model model,
                                  @PageableDefault(value = 9) Pageable pageable) {

        try {
            Page<Conference> pages = conferenceService.findAll(pageable);

            model.addAttribute("conferences", pages);
            model.addAttribute("start", conferenceService.getStartPage(pages));
            model.addAttribute("last", conferenceService.getLastPage(pages));

        } catch (PageNotFound pageNotFound) {
            log.error(pageNotFound.getMessage());
            return "redirect:/conferences?page=1";
        }
        return "index";
    }
}
