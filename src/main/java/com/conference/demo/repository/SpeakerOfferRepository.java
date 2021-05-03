package com.conference.demo.repository;

import com.conference.demo.entities.SpeakerOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeakerOfferRepository extends JpaRepository<SpeakerOffer, Long> {
    boolean existsSpeakerOfferByReportTopicIdAndSpeakerId(long reportTopicId, long speakerId);
}
