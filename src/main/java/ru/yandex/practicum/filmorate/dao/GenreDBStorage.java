package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class GenreDBStorage implements GenreRepository {
    private  final JdbcTemplate jdbcTemplate;


    @Override
    public List<Genre> getAllGenre() {
        return jdbcTemplate.query("SELECT * FROM GENRE", (rs, rowNum) -> createGenre(rs));
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        String sql = "SELECT * FROM GENRES WHERE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> createGenre(rs), id)
                .stream()
                .findFirst();
    }

    @Override
    public void findAllGenreByFilms(List<Film> films) {

        final Map<Long, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));

        String sql = "SELECT * FROM GENRES g " +
                "JOIN film_genres fg ON fg.genre_id = g.id " +
                "WHERE fg.film_id IN (%s)";

        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));

        jdbcTemplate.query(String.format(sql, inSql),
                filmById.keySet().toArray(),
                (rs, rowNum) -> {
                    long filmId = rs.getInt("film_id");
                    Genre genre = createGenre(rs);
                    filmById.get(filmId).getGenres().add(genre);
                    return null;
                });
    }

    private Genre createGenre(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        return new Genre(id, name);
    }
}

