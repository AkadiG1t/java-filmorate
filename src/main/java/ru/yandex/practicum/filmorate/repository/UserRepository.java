package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository {
    void deleteFriend(long userId, long friendId);

    Collection<Long> getAllFriends(long userId);

    Collection<User> getCommonFriends(long userId, long otherId);

    void addFriends(long userId, long friendId);

    Optional<User> get(long id);

    Collection<User> findAll();

    User save(User user);

    User update(User user);

}
