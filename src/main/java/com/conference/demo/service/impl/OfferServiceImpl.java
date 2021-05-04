package com.conference.demo.service.impl;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.dto.TopicOfferDTO;
import com.conference.demo.entities.SpeakerOffer;
import com.conference.demo.entities.TopicOffer;
import com.conference.demo.entities.User;
import com.conference.demo.entities.enums.OfferStatus;
import com.conference.demo.exception.OfferAlreadyExistException;
import com.conference.demo.repository.SpeakerOfferRepository;
import com.conference.demo.repository.TopicOfferRepository;
import com.conference.demo.service.OfferService;
import com.conference.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferServiceImpl implements OfferService {
    private final SpeakerOfferRepository speakerOfferRepository;
    private final UserService userService;
    private final TopicOfferRepository topicOfferRepository;

    @Autowired
    public OfferServiceImpl(SpeakerOfferRepository speakerOfferRepository, UserService userService, TopicOfferRepository topicOfferRepository) {
        this.speakerOfferRepository = speakerOfferRepository;
        this.userService = userService;
        this.topicOfferRepository = topicOfferRepository;
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

    @Override
    public TopicOffer saveTopicOffer(TopicOfferDTO dto, String email, Long conferenceId) throws OfferAlreadyExistException {
        User user = userService.findByEmail(email).orElseThrow();

        if (topicOfferRepository.existsTopicOfferByConferenceIdAndSpeakerId(
                conferenceId, user.getId())) {
            throw new OfferAlreadyExistException(String.format("User '%s' already offered his topic(limit 1 topic) on conference with id: %d", email, conferenceId));
        }
        return topicOfferRepository.save(buildTopicOffer(dto, user, conferenceId));
    }

    private TopicOffer buildTopicOffer(TopicOfferDTO dto, User user, Long conferenceId) {
        return TopicOffer.builder()
                .topic(dto.getTopic())
                .conferenceId(conferenceId)
                .speakerId(user.getId())
                .status(OfferStatus.IN_PROCESS)
                .createdAt(LocalDateTime.now())
                .build();
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
