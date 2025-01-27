package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.assistance.FriendApproved;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendDBStorage implements FriendRepository {
    private  final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(long userId, long friendId) {
        String sql = "INSERT INTO FRIENDS(user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, friendId, FriendApproved.ACCEPTED.toString());
        jdbcTemplate.update(sql, friendId, userId, FriendApproved.NOT_ACCEPTED.toString());
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        String sql = "DELETE FROM FRIENDS WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> getAllFriends(long userId) {
        String sql = "SELECT u.id, u.email, u.login, u.name, u.birthday " +
                "FROM FRIENDS AS f " +
                "JOIN USERS AS u ON u.id = f.friend_id " +
                "WHERE f.user_id = ? " +
                "AND f.status= 'ACCEPTED'" +
                "ORDER BY u.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> createFriend(rs), userId);
    }

    @Override
    public List<User> getCommonFriends(long userId, long otherUserId) {
        String sql = "SELECT u.id, u.email, u.login, u.name, u.birthday " +
                "FROM FRIENDS AS f " +
                "JOIN FRIENDS AS fr ON fr.friend_id = f.friend_id " +
                "JOIN USERS AS u ON u.id = fr.friend_id " +
                "WHERE f.user_id = ? AND fr.user_id = ? " +
                "AND f.friend_id <> fr.user_id " +
                "AND fr.friend_id <> f.user_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> createFriend(rs), userId, otherUserId);
    }

    private User createFriend(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }
}
