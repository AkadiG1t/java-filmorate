package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Repository
public interface UserRepository {
    Collection<User> findAll();

    User save(User user);

    User update(User user);

}
