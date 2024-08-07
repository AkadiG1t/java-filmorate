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
    @NotNull(message = "Чтобы обновить пользователя нужно его ID", groups = Update.class)
    Long id;
    @NotNull(message = "Чтобы зарегестрироваться нужен email", groups = Create.class)
    @Email(message = "must be a well-formed email address")
    String email;
    @NotBlank(message = "must not be blank", groups = Create.class)
    String login;
    String name;
    @BirthdayAnnotation
    @NotNull(message = "must not be null", groups = Create.class)
    private LocalDate birthday;
}
