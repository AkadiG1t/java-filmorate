package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmRepository {
    Collection<Film> getAll();

    Film save(Film film);

    Film update(Film film);

}
