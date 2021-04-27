package com.conference.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateConferenceDTO {
    private String topic;
    private List<String> topicOfReports = new ArrayList<>();
    private LocalDate date;
    private LocalTime time;
    private int numberOfParticipants;
    private String venue;
}
