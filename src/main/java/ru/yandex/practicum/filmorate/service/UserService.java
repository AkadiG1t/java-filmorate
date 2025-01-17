package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import java.util.*;


@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;


    public UserService(@Qualifier("userDBStorage") UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    public void deleteFriend(long userId, long friendId) {
        log.info("Пользователь с ID: {} пытается удалить из друзей пользователя с ID: {}", userId, friendId);

        if (userRepository.get(userId).isEmpty() || userRepository.get(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        friendRepository.removeFriend(userId, friendId);
    }

    public Collection<User> getFriends(long userId) {
        log.info("Получение списка друзей пользователя с ID: {}", userId);
        if (userId == 0) {
            throw new ValidateException("Пожалуйста, напишите id поьзователя");
        }

        if (userRepository.get(userId).isEmpty()) {
            throw new NotFoundException("Такого пользователя нет");
        }

        return friendRepository.getAllFriends(userId);
    }

    public Collection<User> getCommonFriends(long userId, long otherUserId) {
        log.info("Получение общих друзей пользователей с ID: {} и ID: {}", userId, otherUserId);

        return friendRepository.getCommonFriends(userId, otherUserId);
    }

    public void addFriend(long userId, long friendId) {
        log.info("Пользователь с ID: {} пытается добавить в друзья пользователя с ID: {}", userId, friendId);

        if (userRepository.get(userId).isEmpty() || userRepository.get(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        friendRepository.addFriend(userId, friendId);
    }

    public User get (long userId){
       return userRepository.get(userId).orElseThrow(() -> new NotFoundException("Пользователь с id " +
               userId + "не найден"));
    }


    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getLogin().contains(" ")) {
            throw new ValidateException("Логин не может содержать пробелов");
        }

        return userRepository.save(user);
    }

    public User update(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getLogin().contains(" ")) {
            throw new ValidateException("Логин не может содержать пробелов");
        }

        if (user.getId() == null || user.getId() == 0) {
            throw new ValidateException("Чтобы обновить пользователя нужен его id");
        }

        if (userRepository.get(user.getId()).isEmpty()) {
            throw new NotFoundException("Такого пользователя нет");
        }

        return userRepository.update(user);
    }

}
