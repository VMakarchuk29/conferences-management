package com.conference.demo.repository;

import com.conference.demo.entities.ReportTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportTopicRepository extends JpaRepository<ReportTopic, Long> {
    List<ReportTopic> findAllByConferenceIdAndSpeakerIsNull(Long id);
}
