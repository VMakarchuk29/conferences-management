package com.conference.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conference")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String topic;

    @Singular
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    private List<ReportTopic> topicOfReports = new ArrayList<>();

    private LocalDateTime timeOfHolding;
    private int numberOfParticipants;
    private String venue;
}
