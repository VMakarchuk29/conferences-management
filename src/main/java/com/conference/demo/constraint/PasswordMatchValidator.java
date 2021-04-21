package com.conference.demo.constraint;

import com.conference.demo.dto.UserRegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegistrationDTO> {

    @Override
    public void initialize(final PasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRegistrationDTO userRegistrationDTO, ConstraintValidatorContext constraintValidatorContext) {
        return userRegistrationDTO.getPassword() != null && userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword());
    }
}
