package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private static final Map<Long, User> users = new HashMap<>();
    private static Long userId = 0L;

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(++userId);
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException(user.getId());
        }

        User oldUser = users.get(user.getId());

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }

        if (user.getBirthday() != null) {
            oldUser.setBirthday(user.getBirthday());
        }

        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }

        if (user.getLogin() != null) {
            oldUser.setLogin(user.getLogin());
        }

        return oldUser;
    }

}
