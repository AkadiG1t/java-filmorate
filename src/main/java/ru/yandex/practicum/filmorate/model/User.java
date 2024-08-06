package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.ValidateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private String birthday;

    public void validate() throws ValidateException {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new ValidateException("Неверный формат Email");
        }
        if (login == null || login.isBlank() || login.contains(" ")) {
            throw new ValidateException("Неверный формат Логина");
        }
        if (name == null || name.isBlank()) {
            name = login;
        }
        if (birthday != null && LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                .isAfter(LocalDate.now())) {
            throw new ValidateException("Дата рождения не может быть в будущем");
        }
    }
}
