package com.conference.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TopicOfReportDTO {
    @NotEmpty(message = "{invalid.empty}")
    private String topic;
}
