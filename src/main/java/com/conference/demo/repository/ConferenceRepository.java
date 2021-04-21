package com.conference.demo.repository;

import com.conference.demo.entities.Conference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    List<Conference> findAll();

    Page<Conference> findAllByTimeOfHoldingAfter(LocalDateTime time, Pageable pageable);

    Page<Conference> findAllByTimeOfHoldingBefore(LocalDateTime time, Pageable pageable);
}
