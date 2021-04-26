package com.conference.demo.constraint.validator;

import com.conference.demo.constraint.DateRange;
import com.conference.demo.dto.DateRangeDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, DateRangeDTO> {
    @Override
    public void initialize(DateRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(DateRangeDTO value, ConstraintValidatorContext context) {
        return value.getTo().isAfter(value.getFrom());
    }
}
