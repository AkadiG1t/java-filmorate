package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;

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


    public Optional<Film> get(long id) {
        String sql = "SELECT f.*, fr.genre_id, g.name AS genre_name " +
                "FROM FILMS AS f " +
                "LEFT JOIN FILM_GENRES AS fr ON f.id = fr.film_id " +
                "LEFT JOIN GENRES AS g ON fr.genre_id = g.id " +
                "WHERE f.id = ?";

        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Film film = createFilm(rs);
            return film;
        }, id);

        if (!films.isEmpty()) {
            Film film = films.getFirst();


            jdbcTemplate.query(sql, (rs) -> {
                long genreId = rs.getLong("genre_id");
                String genreName = rs.getString("genre_name");

                if (genreId > 0) {
                    Genre genre = new Genre(genreId, genreName);
                    film.getGenres().add(genre); // Добавляем жанр в коллекцию
                }
            }, id);


            return Optional.of(film);
        }

        return Optional.empty();
    }


    @Override
    public Collection<Film> getAll() {
        String sql = "SELECT * FROM FILMS ORDER BY id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> createFilm(rs));
    }

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

                String insertFilmGenreSql = "INSERT INTO FILM_GENRES (film_id, genre_id) VALUES (?, ?)";
                jdbcTemplate.update(insertFilmGenreSql, film.getId(), genre.getId());

            } else {
                throw new ValidateException("Ошибка валидации для жанра с id " + genre.getId());
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
        long mpaId = resultSet.getLong("mpa");

        String sqlMpaName = "SELECT name FROM mpa_rating WHERE id = ?";
        String mpaName = jdbcTemplate.queryForObject(sqlMpaName, new Object[]{mpaId}, String.class);

        return Film.builder()
                .id(id)
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(new Mpa(mpaId, mpaName))
                .build();
    }
}


