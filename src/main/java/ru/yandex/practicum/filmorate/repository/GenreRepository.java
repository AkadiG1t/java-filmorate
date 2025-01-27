package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> getAllGenre();

    Optional<Genre> getGenreById(long id);

    void findAllGenreByFilms(List<Film> films);
}
