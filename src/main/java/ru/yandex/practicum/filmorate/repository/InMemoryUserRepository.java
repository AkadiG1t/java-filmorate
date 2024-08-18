package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserRepository implements UserRepository {
    private static final Map<Long, Set<Long>> friends = new HashMap<>();
    private static final Map<Long, User> users = new HashMap<>();
    private static Long userId = 0L;

    @Override
    public void deleteFriend(long userId, long friendId) {
        friends.get(userId).remove(friendId);
    }

    @Override
    public Collection<User> getAllFriends(long userId) {
       Set<Long> friendsIds = friends.get(userId);

       if (friendsIds == null) {
           return Collections.emptyList();
       }

        return friendsIds.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        Set<Long> usersFriends = friends.get(userId);
        Set<Long> otherUserFriends = friends.get(userId);

        if (usersFriends.isEmpty() || otherUserFriends.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> commonFriends = new HashSet<>(usersFriends);
        commonFriends.retainAll(otherUserFriends);

        return commonFriends.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void addFriends(long userId, long friendId) {
        friends.get(userId).add(friendId);
        friends.get(friendId).add(userId);
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {

        user.setId(++userId);
        users.put(user.getId(), user);
        friends.put(user.getId(), new HashSet<>());

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
