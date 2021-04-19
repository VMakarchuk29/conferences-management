package com.conference.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String topicOfReports; //TODO make List<Topic> topicOfReports
    private LocalDateTime timeOfHolding;
    private int numberOfParticipants;
    private String venue;
}
