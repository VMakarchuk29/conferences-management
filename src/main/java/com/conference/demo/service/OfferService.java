package com.conference.demo.service;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.dto.TopicOfferDTO;
import com.conference.demo.entities.ReportTopic;
import com.conference.demo.entities.SpeakerOffer;
import com.conference.demo.entities.TopicOffer;
import com.conference.demo.exception.OfferAlreadyExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {
    SpeakerOffer saveSpeakerOffer(SpeakerOfferDTO dto, String email) throws OfferAlreadyExistException;

    TopicOffer saveTopicOffer(TopicOfferDTO dto, String email, Long conferenceId) throws OfferAlreadyExistException;

    Page<SpeakerOffer> getSpeakerOffers(Pageable pageable);

    Page<TopicOffer> getTopicOffers(Pageable pageable);

    SpeakerOffer denySpeakerOfferById(Long id);

    SpeakerOffer acceptSpeakerOfferById(Long id);

    ReportTopic denyTopicOfferById(Long id);

    ReportTopic acceptTopicOfferById(Long id);

    Page<SpeakerOffer> getProcessedSpeakerOffers(Pageable pageable);

    Page<TopicOffer> getProcessedTopicOffers(Pageable pageable);
}
