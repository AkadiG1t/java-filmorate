package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    void putLike(long id, long userId);


    void deleteLike(long id, long userId);

    Collection<Film> mostPopularFilms(Long countLikes);

    Collection<Film> getAll();

    Film create(Film film);

    Film update(Film film);

    Film get(Long id);
}
