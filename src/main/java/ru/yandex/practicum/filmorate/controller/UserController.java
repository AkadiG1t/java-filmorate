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
        return userService.get(id);
    }


    @GetMapping
    public Collection<User> users() {
        return userService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid @Validated(Create.class) User user) {
        User createdUser = userService.create(user);

        return createdUser;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody @Valid @Validated(Update.class) User user) {

        return userService.update(user);
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
    public Collection<Long> getAllFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getCommonFriedns(id, otherId);
    }

}
