package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.annotations.BirthdayAnnotation;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.maker.Update;

import java.time.LocalDate;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class User {
    @NotNull(groups = Update.class)
    Long id;
    @Email
    @NotBlank(groups = Create.class)
    String email;
    @NotBlank(groups = Create.class)
    String login;
    String name;
    @BirthdayAnnotation
    @NotNull(groups = Create.class)
    private LocalDate birthday;
}
