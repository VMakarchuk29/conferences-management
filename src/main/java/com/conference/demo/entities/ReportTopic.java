package com.conference.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "report_topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String topic;

    @ManyToOne
    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    private Conference conference;

    @ManyToOne
    @JoinColumn(name = "speaker_id", referencedColumnName = "id")
    private User speaker;

    @OneToMany(mappedBy = "reportTopic", cascade = CascadeType.ALL)
    private List<SpeakerOffer> speakerOffers = new ArrayList<>();
}
