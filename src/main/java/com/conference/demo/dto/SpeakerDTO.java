package com.conference.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpeakerDTO {
    private long speakerId;
    private String firstName;
    private String lastName;
}
