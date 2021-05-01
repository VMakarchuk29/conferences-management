package com.conference.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicOfReportDTO {
    @NotEmpty(message = "{invalid.empty}")
    private String topic;
    @NotNull
    private long speakerId;
}
