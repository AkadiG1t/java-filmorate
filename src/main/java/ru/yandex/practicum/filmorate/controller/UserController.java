package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.data.DataMap;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.maker.Update;
import ru.yandex.practicum.filmorate.model.User;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM,dd");
    private static Long userId = 0L;
    private static final DataMap dataMap = new DataMap();

    @GetMapping
    public Collection<User> users() {
        return new ArrayList<>(dataMap.getUsers().values());
    }

    @PostMapping
    public User create(@RequestBody @Validated(Create.class) User user) {
        log.info("Попытка создать нового пользователя");

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(++userId);
        dataMap.getUsers().put(user.getId(), user);

        log.info("Новый пользователь создан с ID {}", user.getId());

        return user;
    }

    @PutMapping
    public User update(@RequestBody @Validated(Update.class) User user) {
        log.info("Попытка обновления данных пользователя с ID {}", user.getId());

        if (!dataMap.getUsers().containsKey(user.getId())) {
            throw new NotFoundException(user.getId());
        }

        User oldUser = dataMap.getUsers().get(user.getId());

        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }

        if (user.getLogin() != null) {
            oldUser.setLogin(user.getLogin());
        }

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }

        if (user.getBirthday() != null) {
            oldUser.setBirthday(user.getBirthday());
        }

        log.info("Данные пользоваетля с ID {} обновлены", user.getId());

        return oldUser;
    }
}
