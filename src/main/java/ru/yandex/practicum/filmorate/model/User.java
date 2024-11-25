package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.annotations.BirthdayAnnotation;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.marker.Update;

import java.time.LocalDate;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
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
    private LocalDate birthday;

    public void setId(@NotNull(groups = Update.class) Long id) {
        this.id = id;
    }

    public void setEmail(@Email(message = "must be a well-formed email address") @NotBlank(groups = Create.class, message = "must not be blank") String email) {
        this.email = email;
    }

    public void setLogin(@NotBlank(groups = Create.class, message = "must not be blank") String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(@NotNull(groups = Create.class, message = "must not be nul") LocalDate birthday) {
        this.birthday = birthday;
    }

    public @Email(message = "must be a well-formed email address")
    @NotBlank(groups = Create.class, message = "must not be blank") String getEmail() {
        return email;
    }

    public @NotNull(groups = Update.class) Long getId() {
        return id;
    }

    public @NotBlank(groups = Create.class, message = "must not be blank") String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public @NotNull(groups = Create.class, message = "must not be nul") LocalDate getBirthday() {
        return birthday;
    }
}
