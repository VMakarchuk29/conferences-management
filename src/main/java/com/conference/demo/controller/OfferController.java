package com.conference.demo.controller;

import com.conference.demo.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public String showOffers(Model model) {
        model.addAttribute("speakerOffers", offerService.getSpeakerOffers());
        model.addAttribute("topicOffers", offerService.getTopicOffers());
        return "offers";
    }

    @GetMapping("/deny-speaker-offer/{id}")
    public String denySpeakerOffer(@PathVariable("id") Long speakerOfferId) {
        offerService.denySpeakerOfferById(speakerOfferId);
        return "redirect:/offers";
    }

    @GetMapping("/accept-speaker-offer/{id}")
    public String acceptSpeakerOffer(@PathVariable("id") Long speakerOfferId) {
        offerService.acceptSpeakerOfferById(speakerOfferId);
        return "redirect:/offers";
    }

    @GetMapping("/deny-topic-offer/{id}")
    public String denyTopicOffer(@PathVariable("id") Long topicOfferId) {
        offerService.denyTopicOfferById(topicOfferId);
        return "redirect:/offers";
    }

    @GetMapping("/accept-topic-offer/{id}")
    public String acceptTopicOffer(@PathVariable("id") Long topicOfferId) {
        offerService.acceptTopicOfferById(topicOfferId);
        return "redirect:/offers";
    }
}
