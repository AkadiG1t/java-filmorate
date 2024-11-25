package ru.yandex.practicum.filmorate.controller;

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
import ru.yandex.practicum.filmorate.service.UserServiceImpl;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService = new UserServiceImpl();

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        log.info("Получение пользователя с ID: {}", id);
        User user = userService.get(id);
        log.info("Пользователь найден: {}", user);
        return user;
    }

    @GetMapping
    public Collection<User> users() {
        log.info("Получение списка всех пользователей");
        Collection<User> userList = userService.getAll();
        log.info("Количество найденных пользователей: {}", userList.size());
        return userList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid @Validated(Create.class) User user) {
        log.info("Создание нового пользователя: {}", user);
        User createdUser = userService.create(user);
        log.info("Пользователь создан с ID: {}", createdUser.getId());
        return createdUser;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody @Valid @Validated(Update.class) User user) {
        log.info("Обновление пользователя с ID: {}", user.getId());
        User updatedUser = userService.update(user);
        log.info("Данные о пользователе с ID: {} обновлены", updatedUser.getId());
        return updatedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Пользователь с ID: {} пытается добавить в друзья пользователя с ID: {}", id, friendId);
        userService.addFriend(id, friendId);
        log.info("Пользователь с ID: {} добавлен в друзья пользователем с ID: {}", friendId, id);
        return ResponseEntity.ok("Пользователь с ID: " + friendId + " добавлен в друзья.");
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info("Пользователь с ID: {} пытается удалить из друзей пользователя с ID: {}", id, friendId);
        userService.deleteFriend(id, friendId);
        log.info("Пользователь с ID: {} удален из друзей пользователем с ID: {}", friendId, id);
        return ResponseEntity.ok("Пользователь с ID " + friendId + " удален из друзей");
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getAllFriends(@PathVariable long id) {
        log.info("Получение списка друзей пользователя с ID: {}", id);
        Collection<User> friendsList = userService.getFriends(id);
        log.info("Количество друзей пользователя с ID {}: {}", id, friendsList.size());
        return friendsList;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("Получение общих друзей пользователей с ID: {} и ID: {}", id, otherId);
        Collection<User> commonFriends = userService.getCommonFriedns(id, otherId);
        log.info("Количество общих друзей: {}", commonFriends.size());
        return commonFriends;
    }
}
