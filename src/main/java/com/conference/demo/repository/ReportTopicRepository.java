package com.conference.demo.repository;

import com.conference.demo.entities.Conference;
import com.conference.demo.entities.ReportTopic;
import com.conference.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportTopicRepository extends JpaRepository<ReportTopic, Long> {
    List<ReportTopic> findAllByConferenceIdAndSpeakerIsNull(Long id);

    Optional<ReportTopic> findByTopicAndConferenceAndSpeaker(String topic, Conference conference, User speaker);
}
