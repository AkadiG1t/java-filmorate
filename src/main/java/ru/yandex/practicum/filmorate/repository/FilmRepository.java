package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FilmRepository {
    void putLike(long id, long userId);

    Set<Long> getUserId(long id);

    void deleteLike(long id, long userId);

    Collection<Film> mostPopularFilms(long countLikes);

    Optional<Film> get(long id);

    Collection<Film> getAll();

    Film save(Film film);

    Film update(Film film);

}
