package com.conference.demo.service.impl;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.entities.SpeakerOffer;
import com.conference.demo.entities.User;
import com.conference.demo.entities.enums.OfferStatus;
import com.conference.demo.exception.OfferAlreadyExistException;
import com.conference.demo.repository.SpeakerOfferRepository;
import com.conference.demo.service.OfferService;
import com.conference.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferServiceImpl implements OfferService {
    private final SpeakerOfferRepository speakerOfferRepository;
    private final UserService userService;

    @Autowired
    public OfferServiceImpl(SpeakerOfferRepository speakerOfferRepository, UserService userService) {
        this.speakerOfferRepository = speakerOfferRepository;
        this.userService = userService;
    }


    @Override
    public SpeakerOffer saveSpeakerOffer(SpeakerOfferDTO dto, String email) throws OfferAlreadyExistException {
        User user = userService.findByEmail(email).orElseThrow();

        if (speakerOfferRepository.existsSpeakerOfferByReportTopicIdAndSpeakerId(
                dto.getReportTopicId(), user.getId())) {
            throw new OfferAlreadyExistException(String.format("User '%s' already offered on the topic with id: %d", email, dto.getReportTopicId()));
        }

        return speakerOfferRepository.save(buildSpeakerOffer(dto, user));
    }

    private SpeakerOffer buildSpeakerOffer(SpeakerOfferDTO dto, User user) {
        return SpeakerOffer.builder()
                .reportTopicId(dto.getReportTopicId())
                .speakerId(user.getId())
                .status(OfferStatus.IN_PROCESS)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
