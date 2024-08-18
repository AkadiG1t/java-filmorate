package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void deleteFriend(long userId, long friendId) {
        User user = get(userId);
        User friend = get(friendId);

        userRepository.deleteFriend(userId, friendId);
    }

    @Override
    public Collection<User> getFriends(long userId) {
        if (userRepository.getAllFriends(userId).isEmpty()) {
            throw new NotFoundException("У этого пользователя пока нет друзей");
        }

        return userRepository.getAllFriends(userId);
    }

    @Override
    public Collection<User> getCommonFriedns(long userId, long otherId) {
        if (userRepository.getCommonFriends(userId, otherId).isEmpty()) {
            throw new NotFoundException("У этих пользователей нет общих друзей");
        }

        return userRepository.getCommonFriends(userId, otherId);
    }

    @Override
    public void addFriend(long userId, long friendId) {
        User user = get(userId);
        User friend = get(friendId);

        userRepository.addFriends(userId, friendId);
    }

    @Override
    public User get(long userId) {
       if (userRepository.get(userId).isEmpty()) {
           throw new NotFoundException(userId);
       }
       return userRepository.get(userId).get();
    }

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        if (user.getLogin() == null) {
            throw new ValidateException();
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

}