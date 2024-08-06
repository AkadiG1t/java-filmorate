package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotations.NoSpaceAnnotation;

public class NoSpaceValidator implements ConstraintValidator<NoSpaceAnnotation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext Context) {
        if (value == null) {
            return true;
        }

        return !value.contains(" ");
    }
}
