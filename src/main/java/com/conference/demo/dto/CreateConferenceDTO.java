package com.conference.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateConferenceDTO {
    private String topic;
    private String topicOfReports;//TODO make list
    private LocalDate date;
    private LocalTime time;
    private int numberOfParticipants;
    private String venue;
}
