package ru.yandex.practicum.filmorate.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotation.FutureAfter;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<FutureAfter, LocalDate> {

    private LocalDate minDate;

    @Override
    public void initialize(FutureAfter futureAfter) {
        this.minDate = LocalDate.parse(futureAfter.minDate());
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return true;
        }
        return localDate.isAfter(minDate);
    }
}
