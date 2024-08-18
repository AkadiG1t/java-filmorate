package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface FilmRepository {
    void putLike(long id, long userId);


    void deleteLike(long id, long userId);

    Collection<Film> mostPopularFilms(long countLikes);

    Collection<Film> mostPotularFilms();

    Optional<Film> get(long id);

    Collection<Film> getAll();

    Film save(Film film);

    Film update(Film film);

}
