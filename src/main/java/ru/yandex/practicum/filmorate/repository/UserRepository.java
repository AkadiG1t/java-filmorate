package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> get(long id);

    Collection<User> findAll();

    User save(User user);

    User update(User user);

}
