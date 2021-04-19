package com.conference.demo.constraint;

import com.conference.demo.dto.UserRegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    @Override
    public void initialize(final PasswordMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        UserRegistrationDTO user = (UserRegistrationDTO) value;

        return user.getPassword() != null && user.getPassword().equals(user.getConfirmPassword());
    }
}
