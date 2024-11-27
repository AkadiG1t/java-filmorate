package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.InMemoryUserRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository = new InMemoryUserRepository();

    public void deleteFriend(long userId, long friendId) {
        log.info("Пользователь с ID: {} пытается удалить из друзей пользователя с ID: {}", userId, friendId);
        if (userRepository.get(userId).isEmpty()) {
            throw new NotFoundException("Такого пользователя не существует");
        }

        if (userRepository.get(friendId).isEmpty()) {
            throw new NotFoundException("Пользователя которого вы пытаетесь удалить из друзей не существует");
        }

        Set<Long> userFriends = userRepository.get(userId).get().getFriends();
        Set<Long> friend = userRepository.get(friendId).get().getFriends();
        if (userFriends != null) {
            userFriends.remove(friendId);
            friend.remove(userId);
        }
    }


    public Collection<User> getFriends(long userId) {
        if (userRepository.get(userId).isEmpty()) {
            throw new NotFoundException("Такого пользователя не существует");
        }
        log.info("Получение списка друзей пользователя с ID: {}", userId);
        Set<Long> userFriends = userRepository.get(userId).get().getFriends();
        List<User> friendsList = new ArrayList<>();

        if (userFriends == null || userFriends.isEmpty()) {
            return Collections.emptyList();
        }

        for (Long friendId : userFriends) {
            Optional<User> friend = userRepository.get(friendId);
            friend.ifPresent(friendsList::add);
        }
        return friendsList;

    }

    public Collection<User> getCommonFriedns(long userId, long otherId) {
        log.info("Получение общих друзей пользователей с ID: {} и ID: {}", userId, otherId);

        Optional<User> userOpt = userRepository.get(userId);
        Optional<User> otherUserOpt = userRepository.get(otherId);

        if (userOpt.isEmpty() || otherUserOpt.isEmpty()) {
            throw new NotFoundException("Один или оба пользователя не найдены");
        }

        Set<Long> usersFriends = userOpt.get().getFriends();
        Set<Long> otherUserFriends = otherUserOpt.get().getFriends();

        if (usersFriends == null || otherUserFriends == null || usersFriends.isEmpty() || otherUserFriends.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> commonFriendsIds = new HashSet<>(usersFriends);
        commonFriendsIds.retainAll(otherUserFriends);

        if (commonFriendsIds.isEmpty()) {
            throw new NotFoundException("У этих пользователей нет общих друзей");
        }


        return commonFriendsIds.stream()
                .map(userRepository::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

    public void addFriend(long userId, long friendId) {
        log.info("Пользователь с ID: {} пытается добавить в друзья пользователя с ID: {}", userId, friendId);
        if (userRepository.get(userId).isEmpty()) {
            throw new NotFoundException("Этот пользователь не найден");
        }
        if (userRepository.get(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь которого вы пытаетесь добавить в друзья не найден");
        }

        userRepository.get(userId).get().getFriends().add(friendId);
        userRepository.get(friendId).get().getFriends().add(userId);

        }


    public User get(long userId) {
       if (userRepository.get(userId).isEmpty()) {
           throw new NotFoundException(userId);
       }
       return userRepository.get(userId).get();
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
        return userRepository.update(user);
    }

}
