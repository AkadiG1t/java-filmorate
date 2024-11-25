package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.marker.Update;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private Logger log;

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return userService.get(id);
    }


    @GetMapping
    public Collection<User> users() {
        return userService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid @Validated(Create.class) User user) {
        log.info("Попытка создать нового пользователя");
        User createdUser = userService.create(user);
        log.info("Новый пользователь создан с ID {}", user.getId());

        return createdUser;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User update(@RequestBody @Valid @Validated(Update.class) User user) {
        log.info("Попытка обновления данных пользователя с ID {}", user.getId());
        User updatedUser = userService.update(user);
        log.info("Данные пользоваетля с ID {} обновлены", user.getId());

        return updatedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
        return ResponseEntity.ok("Пользователь с id: " + friendId + " добавлен в друзья.");
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
        return ResponseEntity.ok("Пользователь с id " + friendId + " удален из друзей");
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriedns(id, otherId);
    }

}
