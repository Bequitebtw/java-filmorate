package ru.yandex.practicum.filmorate.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validators.DateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureAfter {
    String message() default "Дата должна быть позднее {minDate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String minDate(); // Format

}
