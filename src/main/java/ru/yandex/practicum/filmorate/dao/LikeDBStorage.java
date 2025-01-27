package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.repository.LikeRepository;

@Repository
@RequiredArgsConstructor
public class LikeDBStorage implements LikeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(long filmId, long userId) {
        String sql = "INSERT INTO FILM_LIKES (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        String sql = "DELETE FROM FILM_LIKES WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }
}
