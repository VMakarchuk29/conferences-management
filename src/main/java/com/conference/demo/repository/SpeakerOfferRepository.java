package com.conference.demo.repository;

import com.conference.demo.entities.ReportTopic;
import com.conference.demo.entities.SpeakerOffer;
import com.conference.demo.entities.enums.OfferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeakerOfferRepository extends JpaRepository<SpeakerOffer, Long> {
    boolean existsSpeakerOfferByReportTopicIdAndSpeakerId(long reportTopicId, long speakerId);

    List<SpeakerOffer> findAllByReportTopic(ReportTopic reportTopic);

    Page<SpeakerOffer> findAllByStatusIs(OfferStatus status, Pageable pageable);

}
