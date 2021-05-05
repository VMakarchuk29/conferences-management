package com.conference.demo.service.impl;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.dto.TopicOfferDTO;
import com.conference.demo.entities.ReportTopic;
import com.conference.demo.entities.SpeakerOffer;
import com.conference.demo.entities.TopicOffer;
import com.conference.demo.entities.User;
import com.conference.demo.entities.enums.OfferStatus;
import com.conference.demo.exception.OfferAlreadyExistException;
import com.conference.demo.repository.ReportTopicRepository;
import com.conference.demo.repository.SpeakerOfferRepository;
import com.conference.demo.repository.TopicOfferRepository;
import com.conference.demo.service.OfferService;
import com.conference.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final SpeakerOfferRepository speakerOfferRepository;
    private final UserService userService;
    private final TopicOfferRepository topicOfferRepository;
    private final ReportTopicRepository reportTopicRepository;

    @Autowired
    public OfferServiceImpl(SpeakerOfferRepository speakerOfferRepository, UserService userService, TopicOfferRepository topicOfferRepository, ReportTopicRepository reportTopicRepository) {
        this.speakerOfferRepository = speakerOfferRepository;
        this.userService = userService;
        this.topicOfferRepository = topicOfferRepository;
        this.reportTopicRepository = reportTopicRepository;
    }

    @Override
    public SpeakerOffer saveSpeakerOffer(SpeakerOfferDTO dto, String email) throws OfferAlreadyExistException {
        User user = userService.findByEmail(email).orElseThrow();

        if (speakerOfferRepository.existsSpeakerOfferByReportTopicIdAndSpeakerId(
                dto.getReportTopicId(), user.getId())) {
            throw new OfferAlreadyExistException(String.format("User '%s' already offered on the topic with id: %d", email, dto.getReportTopicId()));
        }
        ReportTopic reportTopic = reportTopicRepository.findById(dto.getReportTopicId()).orElseThrow();//TODO remaster

        SpeakerOffer speakerOffer = buildSpeakerOffer(reportTopic, user);

        user.getSpeakerOffers().add(speakerOffer);
        reportTopic.getSpeakerOffers().add(speakerOffer);

        return speakerOfferRepository.save(speakerOffer);
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

    @Override
    public List<SpeakerOffer> getSpeakerOffers() {
        return speakerOfferRepository.findAll();
    }

    @Override
    public List<TopicOffer> getTopicOffers() {
        return topicOfferRepository.findAll();
    }

    @Override
    @Transactional
    public SpeakerOffer denySpeakerOfferById(Long id) {
        SpeakerOffer speakerOffer = speakerOfferRepository.findById(id).orElseThrow();
        speakerOffer.setStatus(OfferStatus.DENIED);

        ReportTopic reportTopic = speakerOffer.getReportTopic();
        reportTopic.setSpeaker(null);
        reportTopicRepository.save(reportTopic);
        return speakerOfferRepository.save(speakerOffer);
    }

    @Override
    @Transactional
    public SpeakerOffer acceptSpeakerOfferById(Long id) {
        SpeakerOffer speakerOffer = speakerOfferRepository.findById(id).orElseThrow();

        speakerOfferRepository.findAllByReportTopic(speakerOffer.getReportTopic()).forEach(offer -> {
            offer.setStatus(OfferStatus.DENIED);
            speakerOfferRepository.save(offer);
        });

        speakerOffer.getReportTopic().setSpeaker(speakerOffer.getSpeaker());
        speakerOffer.getSpeaker().getSpeakersReports().add(speakerOffer.getReportTopic());
        reportTopicRepository.save(speakerOffer.getReportTopic());

        speakerOffer.setStatus(OfferStatus.ACCEPT);

        return speakerOfferRepository.save(speakerOffer);
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

    private SpeakerOffer buildSpeakerOffer(ReportTopic reportTopic, User user) {
        return SpeakerOffer.builder()
                .reportTopic(reportTopic)
                .speaker(user)
                .status(OfferStatus.IN_PROCESS)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
