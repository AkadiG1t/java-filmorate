package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping
    public Collection<User> users() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        log.info("Попытка создать нового пользователя");
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        User createdUser = userService.createUser(user);
        log.info("Новый пользователь создан с ID {}", user.getId());

        return createdUser;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.info("Попытка обновления данных пользователя с ID {}", user.getId());
        User updatedUser = userService.updateUser(user);
        log.info("Данные пользоваетля с ID {} обновлены", user.getId());

        return updatedUser;
    }
}
