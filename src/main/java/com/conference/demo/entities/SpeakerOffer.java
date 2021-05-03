package com.conference.demo.entities;

import com.conference.demo.entities.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "speaker_offer")
@AllArgsConstructor
@NoArgsConstructor
public class SpeakerOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long reportTopicId;

    private long speakerId;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
