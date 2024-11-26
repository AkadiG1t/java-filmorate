package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.marker.Update;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        User user = userService.get(id);

        return user;
    }

    @GetMapping
    public Collection<User> users() {
        Collection<User> userList = userService.getAll();

        return userList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid @Validated(Create.class) User user) {
        User createdUser = userService.create(user);

        return createdUser;
    }

    @PutMapping
    public User update(@RequestBody @Valid @Validated(Update.class) User user) {
        User updatedUser = userService.update(user);

        return updatedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Collection<User>> addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);

        return ResponseEntity.ok(userService.getFriends(id));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
        return ResponseEntity.ok("Пользователь с ID " + friendId + " удален из друзей");
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllFriends(@PathVariable long id) {
        Collection<User> friendsList = userService.getFriends(id);

        return friendsList;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        Collection<User> commonFriends = userService.getCommonFriedns(id, otherId);

        return commonFriends;
    }
}
