package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmRepository implements FilmRepository {
    private static final Map<Long, Set<Long>> likes = new HashMap<>();
    private static final Map<Long, Film> films = new HashMap<>();
    private static Long filmId = 0L;

    @Override
    public void putLike(long id, long userId) {
        likes.get(id).add(userId);
    }

    @Override
    public void deleteLike(long id, long userId) {
        likes.get(id).remove(userId);
    }

    @Override
    public Collection<Film> mostPopularFilms(long countLikes) {
        Set<Long> popularFilms = likes.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= countLikes)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return popularFilms.stream()
                .map(films::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Film> mostPotularFilms() {
        return likes.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(), entry1.getValue().size()))
                .limit(10)
                .map(Map.Entry::getKey)
                .map(films::get)
                .filter(Objects::nonNull)
                .toList();
    }



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
        likes.put(film.getId(), new HashSet<>());

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
