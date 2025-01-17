package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.MpaRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpaRatingDBStorage implements MpaRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("SELECT * FROM MPA_RATING", (rs, rowNum) -> createMpa(rs));
    }

    @Override
    public Optional<Mpa> getMpaById(long id) {
        String sql = "SELECT COUNT(*) FROM MPA_RATING WHERE id = ?";
        int countMPA = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);

        if (countMPA > 0) {
            return jdbcTemplate.query(sql, (rs, rowNum) -> createMpa(rs), id)
                    .stream()
                    .findFirst();
        } else {
            throw new NotFoundException("Не найден жанр с ID " + id);
        }
    }

    private Mpa createMpa(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        return new Mpa(id, name);
    }
}

