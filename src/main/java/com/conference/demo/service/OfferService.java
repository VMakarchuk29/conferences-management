package com.conference.demo.service;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.dto.TopicOfferDTO;
import com.conference.demo.entities.SpeakerOffer;
import com.conference.demo.entities.TopicOffer;
import com.conference.demo.exception.OfferAlreadyExistException;

import java.util.List;

public interface OfferService {
    SpeakerOffer saveSpeakerOffer(SpeakerOfferDTO dto, String email) throws OfferAlreadyExistException;

    TopicOffer saveTopicOffer(TopicOfferDTO dto, String email, Long conferenceId) throws OfferAlreadyExistException;

    List<SpeakerOffer> getSpeakerOffers();

    List<TopicOffer> getTopicOffers();

    SpeakerOffer denySpeakerOfferById(Long id);

    SpeakerOffer acceptSpeakerOfferById(Long id);
}
