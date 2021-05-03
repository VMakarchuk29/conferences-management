package com.conference.demo.service;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.entities.SpeakerOffer;
import com.conference.demo.exception.OfferAlreadyExistException;

public interface OfferService {
    SpeakerOffer saveSpeakerOffer(SpeakerOfferDTO dto, String email) throws OfferAlreadyExistException;
}
