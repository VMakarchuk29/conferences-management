package com.conference.demo.dto;

import com.conference.demo.constraint.PasswordMatch;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

@PasswordMatch(first = "password", second = "confirmPassword", message = "{invalid.password-match}")
@Data
public class UserRegistrationDTO {
    private static final String USER_PHONE_REGEX = "^\\+380[0-9]{9}";

    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String firstName;

    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String lastName;

    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String userName;

    @Email
    @NotEmpty(message = "{invalid.empty}")
    @Size(max = 50)
    private String email;

    @NotEmpty(message = "{invalid.empty}")
    @Size(min = 4, max = 20)
    private String password;

    @NotEmpty(message = "{invalid.empty}")
    @Size(min = 4, max = 20)
    private String confirmPassword;

    @NotEmpty(message = "{invalid.empty}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;

    @NotEmpty(message = "{invalid.empty}")
    @Pattern(regexp = USER_PHONE_REGEX, message = "{invalid.phone-number}")
    private String phoneNumber;

    @NotEmpty(message = "{invalid.gender}")
    private String gender;

    @AssertTrue
    private Boolean terms;
}
