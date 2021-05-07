package com.conference.demo.controller;

import com.conference.demo.service.OfferService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/offers")
@PreAuthorize("hasAuthority('MODERATOR')")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public String showOffers(Model model,
                             @Qualifier("p1") Pageable pageable1,
                             @Qualifier("p2") Pageable pageable2) {

        model.addAttribute("speakerOffers", offerService.getSpeakerOffers(pageable1));
        model.addAttribute("topicOffers", offerService.getTopicOffers(pageable2));
        return "offers";
    }

    @GetMapping("/processed")
    public String showProcessedOffers(Model model,
                                      @Qualifier("p1") Pageable pageable1,
                                      @Qualifier("p2") Pageable pageable2) {

        model.addAttribute("processedSpeakerOffers", offerService.getProcessedSpeakerOffers(pageable1));
        model.addAttribute("processedTopicOffers", offerService.getProcessedTopicOffers(pageable2));
        return "processed-offers";
    }

    @GetMapping("/deny-speaker-offer/{id}")
    public String denySpeakerOffer(@PathVariable("id") Long speakerOfferId,
                                   @RequestParam(defaultValue = "") String url) {
        offerService.denySpeakerOfferById(speakerOfferId);
        return String.format("redirect:/offers%s", url);
    }

    @GetMapping("/accept-speaker-offer/{id}")
    public String acceptSpeakerOffer(@PathVariable("id") Long speakerOfferId,
                                     @RequestParam(defaultValue = "") String url) {
        offerService.acceptSpeakerOfferById(speakerOfferId);
        return String.format("redirect:/offers%s", url);
    }

    @GetMapping("/deny-topic-offer/{id}")
    public String denyTopicOffer(@PathVariable("id") Long topicOfferId,
                                 @RequestParam(defaultValue = "") String url) {
        offerService.denyTopicOfferById(topicOfferId);
        return String.format("redirect:/offers%s?topic", url);
    }

    @GetMapping("/accept-topic-offer/{id}")
    public String acceptTopicOffer(@PathVariable("id") Long topicOfferId,
                                   @RequestParam(defaultValue = "") String url) {
        offerService.acceptTopicOfferById(topicOfferId);
        return String.format("redirect:/offers%s?topic", url);
    }

}
