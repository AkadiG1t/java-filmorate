package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class FilmDBStorage implements FilmRepository {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Film> get(long id) {
        String sql = "SELECT * FROM FILMS WHERE id = ?";

        return jdbcTemplate.query(sql,(rs, rowNum) ->  createFilm(rs), id)
                .stream()
                .findFirst();
    }

    @Override
    public Collection<Film> getAll() {
        String sql = "SELECT * FROM FILMS ORDER BY id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> createFilm(rs));
    }

    @Override
    public Film save(Film film) {
        String sql = "INSERT INTO FILMS (name, description, release_Date, duration, mpa) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String checkMpaSql = "SELECT COUNT(*) FROM mpa_rating WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkMpaSql, new Object[]{film.getMpa().getId()}, Integer.class);

        if (count > 0) {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, film.getName());
                ps.setString(2, film.getDescription());
                ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                ps.setInt(4, film.getDuration());
                ps.setLong(5, film.getMpa().getId());
                return ps;
            }, keyHolder);
        } else {

            throw new ValidateException("MPA с id " + film.getMpa().getId() + " не существует.");
        }

        film.setId(keyHolder.getKey().longValue());

        for (Genre genre : film.getGenres()) {
            String checkGenreSql = "SELECT COUNT(*) FROM genres WHERE id = ?";
            int genreCount = jdbcTemplate.queryForObject(checkGenreSql, new Object[]{genre.getId()}, Integer.class);

            if (genreCount > 0) {
                String getName = "SELECT name FROM genres WHERE id = ?";
                String genreName = jdbcTemplate.queryForObject(getName, new Object[]{genre.getId()}, String.class);
                genre.setName(genreName);
            } else {
                throw new ValidateException("Ошибка валидации");
            }
        }

        return film;
    }

    @Override
    public Film update(Film film) {
        long id = film.getId();

        String countSql = "SELECT COUNT(*) FROM FILMS WHERE id = ?";
        int countFilms = jdbcTemplate.queryForObject(countSql, new Object[]{film.getId()}, Integer.class);

        if (countFilms > 0) {

            String sql = "UPDATE FILMS SET name = ?, description = ?, release_date = ?, duration = ?, mpa = ? " +
                "WHERE id = ?";

            jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                    film.getMpa().getId(), id);
        } else {
            throw new RuntimeException("Фильм с id " + id + " не найден.");
        }

        return film;
    }

    @Override
    public List<Film> getMostPopular(int count) {
        String sql =  "SELECT f.*, COUNT(fl.film_id) AS like_count " +
                "FROM FILMS AS f " +
                "LEFT JOIN FILM_LIKES AS fl ON f.id = fl.film_id " +
                "GROUP BY f.id " +
                "ORDER BY like_count DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> createFilm(rs), count);
    }


    private Film createFilm(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        return Film.builder()
                .id(id)
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(new Mpa(resultSet.getLong("mpa"),
                        resultSet.getString("name")))
                .build();
    }

    private void setGenres(Set<Genre> genres, long id) {
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id = ?", id);

        if (!genres.isEmpty()) {
            String sql = "INSERT INTO FILM_GENRES (film_id, genre_id) VALUES (?, ?)";

            Genre[] g = genres.toArray(new Genre[genres.size()]);
            jdbcTemplate.batchUpdate(
                    sql,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, id);
                            ps.setLong(2, g[i].getId());
                        }

                        @Override
                        public int getBatchSize() {
                            return genres.size();
                        }
                    });
        }
    }
}

