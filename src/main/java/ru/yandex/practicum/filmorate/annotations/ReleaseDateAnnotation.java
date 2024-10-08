package ru.yandex.practicum.filmorate.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validator.ReleaseDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ReleaseDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReleaseDateAnnotation {
    String message() default "Некорректная дата";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
