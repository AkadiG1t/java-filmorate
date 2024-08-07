package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

public class InMemoryFilmRepository implements FilmRepository {
    private static final Map<Long, Film> films = new HashMap<>();
    private static Long filmId = 0L;

    @Override
    public Collection<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film save(Film film) {

        film.setId(++filmId);
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException(film.getId());
        }

        Film oldFilm = films.get(film.getId());

        if (film.getDuration() != null) {
            oldFilm.setDuration(film.getDuration());
        }

        if (film.getReleaseDate() != null) {
            oldFilm.setReleaseDate(film.getReleaseDate());
        }

        if (film.getName() != null) {
            oldFilm.setName(film.getName());
        }

        if (film.getDescription() != null) {
            oldFilm.setDescription(film.getDescription());
        }

        return oldFilm;
    }

}
