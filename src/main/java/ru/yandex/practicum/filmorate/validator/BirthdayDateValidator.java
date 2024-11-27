package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotations.BirthdayAnnotation;
import ru.yandex.practicum.filmorate.exception.ValidateException;

import java.time.LocalDate;


public class BirthdayDateValidator implements ConstraintValidator<BirthdayAnnotation, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.isAfter(LocalDate.now())) {
            throw new ValidateException("asd");
        }

        return true;
    }
}

