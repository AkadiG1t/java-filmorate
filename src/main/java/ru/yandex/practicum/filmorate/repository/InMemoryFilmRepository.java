package ru.yandex.practicum.filmorate.repository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmRepository implements FilmRepository {
    @Getter
    private static final Map<Long, Film> films = new HashMap<>();
    private static Long filmId = 0L;


    @Override
    public Optional<Film> get(long id) {
        return Optional.ofNullable(films.get(id));
    }

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
        log.info("Обновляем данные фильма с ID: {}", film.getId());
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException(film.getId());
        }

        Film oldFilm = films.get(film.getId());

        Optional.ofNullable(film.getDuration()).ifPresent(oldFilm::setDuration);
        Optional.ofNullable(film.getReleaseDate()).ifPresent(oldFilm::setReleaseDate);
        Optional.ofNullable(film.getName()).ifPresent(oldFilm::setName);
        Optional.ofNullable(film.getDescription()).ifPresent(oldFilm::setDescription);

        return oldFilm;
    }
}
