package com.conference.demo.controller;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.exception.OfferAlreadyExistException;
import com.conference.demo.service.OfferService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/speaker")
public class SpeakerController {
    private static final Logger log = Logger.getLogger(SpeakerController.class);
    private final OfferService offerService;

    @Autowired
    public SpeakerController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/offer-yourself/{id}")
    public String offerYourself(
            @ModelAttribute SpeakerOfferDTO dto,
            Principal principal,
            @PathVariable("id") Long conference_id) {

        log.info(String.format("User '%s' offered himself as a speaker on the topic with id: %d", principal.getName(), dto.getReportTopicId()));

        try {
            log.info(String.format("Offer by User '%s' saved with id = %d",
                    principal.getName(),
                    offerService.saveSpeakerOffer(dto, principal.getName()).getSpeakerId()));
        } catch (OfferAlreadyExistException e) {
            log.error(e.getMessage());
            return "redirect:/conference-details/" + conference_id + "?speakerOfferError";
        }

        return "redirect:/conference-details/" + conference_id + "?speakerOfferSuccess";
    }

    @PostMapping("/offer-topic")
    public String offerTopic() {
        return "";
    }
}
