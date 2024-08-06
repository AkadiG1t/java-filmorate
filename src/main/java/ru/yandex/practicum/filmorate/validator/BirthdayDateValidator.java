package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotations.BirthdayAnnotation;
import java.time.LocalDate;

public class BirthdayDateValidator implements ConstraintValidator<BirthdayAnnotation, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !value.isAfter(LocalDate.now());
    }
}

