package com.conference.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConferenceDTO {
    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String topic;

    @NotEmpty(message = "{invalid.empty}")
    private List<@Valid TopicOfReportDTO> topicOfReports = new ArrayList<>();

    @NotNull(message = "{invalid.empty}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "{invalid.empty}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    @Min(value = 1)
    private int numberOfParticipants;

    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String venue;
}
