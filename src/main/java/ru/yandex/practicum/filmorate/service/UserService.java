package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.InMemoryUserRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository = new InMemoryUserRepository();

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getName());
        }
        if (user.getLogin() == null) {
            throw new ValidateException();
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }
}
