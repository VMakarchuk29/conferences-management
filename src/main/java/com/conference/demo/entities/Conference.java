package com.conference.demo.entities;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conference")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String topic;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<ReportTopic> topicOfReports = new ArrayList<>();

    @Formula("(select count(*) from report_topic where report_topic.conference_id=id)")
    private int topicCount;

    private LocalDateTime timeOfHolding;
    private int numberOfParticipants;
    private String venue;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<TopicOffer> topicOffers = new ArrayList<>();
}
