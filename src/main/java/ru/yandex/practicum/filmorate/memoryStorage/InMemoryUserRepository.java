package ru.yandex.practicum.filmorate.memoryStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserRepository implements UserRepository {
    private static final Map<Long, User> users = new HashMap<>();
    private static Long userId = 0L;

    public Collection<User> getAllUsers() {
        return users.values();
    }


    @Override
    public Optional<User> get(long id) {
        log.info("Получение пользователя с ID: {}", id);
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Collection<User> findAll() {
        log.info("Получение списка всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {
        log.info("Создание нового пользователя: {}", user);
        user.setId(++userId);
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        log.info("Обновление пользователя с ID: {}", user.getId());

        if (!users.containsKey(user.getId())) {
            throw new NotFoundException(user.getId());
        }

        User oldUser = users.get(user.getId());

        Optional.ofNullable(user.getName()).ifPresent(oldUser::setName);
        Optional.ofNullable(user.getBirthday()).ifPresent(oldUser::setBirthday);
        Optional.ofNullable(user.getEmail()).ifPresent(oldUser::setEmail);
        Optional.ofNullable(user.getLogin()).ifPresent(oldUser::setLogin);

        return oldUser;
    }
}
