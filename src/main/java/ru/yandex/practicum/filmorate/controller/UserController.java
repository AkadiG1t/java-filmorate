package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @GetMapping
    public Collection<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getEmail() == null ||user.getEmail().isBlank() || !user.getEmail().contains("@") ) {
            log.error("Неверный формат Email {}", user.getEmail());
            throw new ValidateException("Неверный формат Email");
        }

        if (user.getLogin() == null || user.getLogin().isBlank()|| user.getLogin().contains(" ")) {
            log.error("Неверный формат Логина {}", user.getLogin());
            throw new ValidateException("Неверный формат Логина");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (LocalDate.parse(user.getBirthday(), dateTimeFormatter).isAfter(LocalDate.now())) {
            log.error("Невалидная дата рождения{}", user.getBirthday());
            throw new ValidateException("Дата рождения не может быть в будущем");
        }

        user.setId(nextId());
        users.put(user.getId(), user);

        log.info("Пользователь успешно создан.{}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            log.error("Не переданно id, не может быть выполнен поиск {}", (Object) null);
            throw new ValidateException("Чтобы обновить данные нужен id");
        }

        Optional<User> optionalUser = Optional.ofNullable(users.get(user.getId()));

        if (optionalUser.isEmpty()) {
            log.error("Не найден пользователь с указанным id {}", user.getId());
            throw new NotFoundException("Пользователь с id " + user.getId() +  " не найден");
        }

        User oldUser = optionalUser.get();

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }

        if (user.getBirthday() != null) {
            if (LocalDate.parse(user.getBirthday(), dateTimeFormatter).isAfter(LocalDate.now())) {
                oldUser.setBirthday(user.getBirthday());
            } else {
                log.error("Некорректная дата рождения {}", user.getBirthday());
                throw new ValidateException("День рождения не может быть в будущем");
            }
        }

        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }

        log.info("Пользователь с id {} обновлен", oldUser.getId());
        return oldUser;
    }

    private long nextId() {
        long nextId = users.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++nextId;
    }
}
