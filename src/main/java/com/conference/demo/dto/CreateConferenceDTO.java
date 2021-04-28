package com.conference.demo.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateConferenceDTO {
    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String topic;

    @NotEmpty(message = "{invalid.empty}")
    private List<@Valid TopicOfReportDTO> topicOfReports = new ArrayList<>();

    @NotNull(message = "{invalid.empty}")
    private LocalDate date;

    @NotNull(message = "{invalid.empty}")
    private LocalTime time;

    @Min(value = 1)
    private int numberOfParticipants;

    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String venue;
}
