package com.conference.demo.repository;

import com.conference.demo.entities.TopicOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicOfferRepository extends JpaRepository<TopicOffer, Long> {
}
