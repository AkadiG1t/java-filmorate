package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    void deleteFriend(long userId, long friendId);

    Collection<User> getFriends(long userId);

    Collection<User> getCommonFriedns(long userId, long otherId);

    void addFriend(long userId, long friendId);

    User get(long userId);

    Collection<User> getAll();

    User create(User user);

    User update(User user);

}
