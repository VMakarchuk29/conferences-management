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

    @ManyToOne
    @JoinColumn(name = "report_topic_id", referencedColumnName = "id")
    private ReportTopic reportTopic;

    @ManyToOne
    @JoinColumn(name = "speaker_id", referencedColumnName = "id")
    private User speaker;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
}
