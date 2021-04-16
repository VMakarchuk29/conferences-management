package com.conference.demo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDTO {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String userName;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min = 4)
    private String password;
    @NotEmpty
    @Size(min = 4)
    private String confirmPassword;
    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;
    @NotEmpty
    @Size(min = 10, max = 13)
    private String phoneNumber;
    @NotEmpty
    private String gender;

    @AssertTrue
    private Boolean terms;
}
