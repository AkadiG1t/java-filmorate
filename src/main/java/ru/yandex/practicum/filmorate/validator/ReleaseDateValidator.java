package ru.yandex.practicum.filmorate.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAnnotation;
import ru.yandex.practicum.filmorate.exception.ValidateException;

import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateAnnotation, LocalDate> {
    private static final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.isBefore(MIN_DATE)) {
            throw new ValidateException("Дата релиза фильма не может быть ранее чем 28.12.1895");
        }

        return true;
    }
}
