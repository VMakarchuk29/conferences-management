package com.conference.demo.controller;

import com.conference.demo.exception.AlreadyRegisteredException;
import com.conference.demo.service.ConfRegistrationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/conference")
public class RegOnConferenceController {
    private static final Logger log = Logger.getLogger(RegOnConferenceController.class);
    private final ConfRegistrationService confRegistrationService;

    @Autowired
    public RegOnConferenceController(ConfRegistrationService confRegistrationService) {
        this.confRegistrationService = confRegistrationService;
    }

    @GetMapping("/registration/{conference_id}")
    public String registration(@PathVariable("conference_id") long conferenceId, Principal principal) {
        log.info(String.format("User '%s' wants to register for the conference, with id = %d", principal.getName(), conferenceId));

        try {
            confRegistrationService.registerUser(principal.getName(), conferenceId);
        } catch (AlreadyRegisteredException e) {
            log.error(e.getMessage());
            return String.format("redirect:/conference-details/%s?errorReg", conferenceId);
        }

        log.info(String.format("User '%s' successfully registered for the conference, with id = %d", principal.getName(), conferenceId));

        return String.format("redirect:/conference-details/%s?successReg", conferenceId);
    }
}
