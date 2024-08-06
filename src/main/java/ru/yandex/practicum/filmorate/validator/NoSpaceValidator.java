package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotations.NoSpaceAnnotation;
import ru.yandex.practicum.filmorate.exception.ValidateException;

public class NoSpaceValidator implements ConstraintValidator<NoSpaceAnnotation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.contains(" ")) {
            throw new ValidateException("");
        }

        return true;
    }
}
