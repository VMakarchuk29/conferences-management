package com.conference.demo.repository;

import com.conference.demo.entities.TopicOffer;
import com.conference.demo.entities.enums.OfferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicOfferRepository extends JpaRepository<TopicOffer, Long> {
    boolean existsTopicOfferByConferenceIdAndSpeakerId(long conferenceId, long speakerId);

    Page<TopicOffer> findAllByStatusIs(OfferStatus status, Pageable pageable);

}
