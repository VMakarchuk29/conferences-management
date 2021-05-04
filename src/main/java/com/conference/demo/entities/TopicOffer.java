package com.conference.demo.entities;

import com.conference.demo.entities.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "topic_offer")
public class TopicOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topic;

    private long conferenceId;

    private long speakerId;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
