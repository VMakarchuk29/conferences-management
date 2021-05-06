package com.conference.demo.service.impl;

import com.conference.demo.dto.SpeakerOfferDTO;
import com.conference.demo.dto.TopicOfferDTO;
import com.conference.demo.entities.*;
import com.conference.demo.entities.enums.OfferStatus;
import com.conference.demo.exception.OfferAlreadyExistException;
import com.conference.demo.repository.ConferenceRepository;
import com.conference.demo.repository.ReportTopicRepository;
import com.conference.demo.repository.SpeakerOfferRepository;
import com.conference.demo.repository.TopicOfferRepository;
import com.conference.demo.service.OfferService;
import com.conference.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OfferServiceImpl implements OfferService {
    private final SpeakerOfferRepository speakerOfferRepository;
    private final UserService userService;
    private final TopicOfferRepository topicOfferRepository;
    private final ReportTopicRepository reportTopicRepository;
    private final ConferenceRepository conferenceRepository;


    @Autowired
    public OfferServiceImpl(SpeakerOfferRepository speakerOfferRepository, UserService userService, TopicOfferRepository topicOfferRepository, ReportTopicRepository reportTopicRepository, ConferenceRepository conferenceRepository) {
        this.speakerOfferRepository = speakerOfferRepository;
        this.userService = userService;
        this.topicOfferRepository = topicOfferRepository;
        this.reportTopicRepository = reportTopicRepository;
        this.conferenceRepository = conferenceRepository;
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
        Conference conference = conferenceRepository.findById(conferenceId).orElseThrow();

        TopicOffer topicOffer = buildTopicOffer(dto, user, conference);

        user.getTopicOffers().add(topicOffer);
        conference.getTopicOffers().add(topicOffer);

        return topicOfferRepository.save(topicOffer);
    }

    @Override
    public Page<SpeakerOffer> getSpeakerOffers(Pageable pageable) {
        return speakerOfferRepository.findAllByStatusIs(OfferStatus.IN_PROCESS, pageable);
    }

    @Override
    public Page<TopicOffer> getTopicOffers(Pageable pageable) {
        return topicOfferRepository.findAllByStatusIs(OfferStatus.IN_PROCESS, pageable);
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

    @Override
    @Transactional
    public ReportTopic denyTopicOfferById(Long id) {
        TopicOffer topicOffer = topicOfferRepository.findById(id).orElseThrow();
        topicOffer.setStatus(OfferStatus.DENIED);

        ReportTopic reportTopic = reportTopicRepository.findByTopicAndConferenceAndSpeaker(
                topicOffer.getTopic(),
                topicOffer.getConference(),
                topicOffer.getSpeaker()
        ).orElseThrow();

        reportTopicRepository.delete(reportTopic);
        return reportTopic;
    }

    @Override
    @Transactional
    public ReportTopic acceptTopicOfferById(Long id) {
        TopicOffer topicOffer = topicOfferRepository.findById(id).orElseThrow();

        topicOffer.setStatus(OfferStatus.ACCEPT);

        ReportTopic reportTopic = ReportTopic.builder()
                .topic(topicOffer.getTopic())
                .conference(topicOffer.getConference())
                .speaker(topicOffer.getSpeaker())
                .build();
        Conference conference = topicOffer.getConference();
        conference.getTopicOfReports().add(reportTopic);

        User speaker = topicOffer.getSpeaker();
        speaker.getSpeakersReports().add(reportTopic);


        return reportTopicRepository.save(reportTopic);
    }

    @Override
    public Page<SpeakerOffer> getProcessedSpeakerOffers(Pageable pageable) {
        return speakerOfferRepository.findAllByStatusIsNot(OfferStatus.IN_PROCESS, pageable);
    }

    @Override
    public Page<TopicOffer> getProcessedTopicOffers(Pageable pageable) {
        return topicOfferRepository.findAllByStatusIsNot(OfferStatus.IN_PROCESS, pageable);
    }

    private TopicOffer buildTopicOffer(TopicOfferDTO dto, User user, Conference conference) {
        return TopicOffer.builder()
                .topic(dto.getTopic())
                .conference(conference)
                .speaker(user)
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
