package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findAll();

    User save(User user);

    User update(User user);

}
