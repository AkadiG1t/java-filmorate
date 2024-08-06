package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.BirthdayAnnotation;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.maker.Update;

import java.time.LocalDate;


@Data
public class User {
    @NotNull(message = "Чтобы обновить пользователя нужно его ID", groups = Update.class)
    private Long id;
    @NotNull(message = "Чтобы зарегестрироваться нужен email", groups = Create.class)
    @Email(message = "must be a well-formed email address")
    private String email;
    @NotNull(message = "must not be null", groups = Create.class)
    @NotBlank
    private String login;
    private String name;
    @BirthdayAnnotation
    @NotNull(message = "must not be null", groups = Create.class)
    private LocalDate birthday;
}
