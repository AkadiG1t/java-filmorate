package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Repository
public interface FilmRepository {
    Collection<Film> getAll();

    Film save(Film film);

    Film update(Film film);

}
