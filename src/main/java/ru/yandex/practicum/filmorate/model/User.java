package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.BirthdayAnnotation;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.maker.Update;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
public class User {
    @NotNull(message = "Чтобы обновить пользователя нужно его ID", groups = Update.class)
    private Long id;
    @NotNull(message = "Чтобы зарегестрироваться нужен email", groups = Create.class)
    @Email
    private String email;
    @NotNull(groups = Create.class)
    private String login;
    private String name;
    @JsonFormat(shape = STRING, pattern = "yyyy.MM.dd")
    @BirthdayAnnotation
    @NotNull(groups = Create.class)
    private LocalDate birthday;
}
