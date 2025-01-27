package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository {

    Optional<Film> get(long id);

    Collection<Film> getAll();

    Film save(Film film);

    Film update(Film film);

    List<Film> getMostPopular(int count);
}
