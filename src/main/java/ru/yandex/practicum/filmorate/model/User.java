package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.annotations.BirthdayAnnotation;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.marker.Update;
import java.time.LocalDate;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class User {
    @NotNull(groups = Update.class)
    Long id;
    @Email(message = "must be a well-formed email address")
    @NotBlank(groups = Create.class, message = "must not be blank")
    String email;
    @NotBlank(groups = Create.class, message = "must not be blank")
    String login;
    String name;
    @BirthdayAnnotation
    @NotNull(groups = Create.class, message = "must not be nul")
    LocalDate birthday;
}
