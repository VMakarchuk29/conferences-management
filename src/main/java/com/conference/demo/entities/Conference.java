package com.conference.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "conference")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String topic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    private List<ReportTopic> topicOfReports;

    private LocalDateTime timeOfHolding;
    private int numberOfParticipants;
    private String venue;
}
