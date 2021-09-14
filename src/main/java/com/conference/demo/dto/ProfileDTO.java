package com.conference.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
public class ProfileDTO {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String userName;

    @Email
    @NotEmpty
    private String email;

//    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String gender;

    private String filename;
}
