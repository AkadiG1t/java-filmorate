package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmRepository implements FilmRepository {
    public static final Map<Long, Set<Long>> likes = new HashMap<>();
    private static final Map<Long, Film> films = new HashMap<>();
    private static Long filmId = 0L;

    @Override
    public void putLike(long id, long userId) {
        if (id > 0 && userId > 0) {
            if (likes.containsKey(id)) {
                likes.get(id).add(userId);
            } else {
                likes.put(id, new HashSet<>());
                likes.get(id).add(userId);
            }
        }
    }

    @Override
    public Set<Long> getUserId(long id) {
        return new HashSet<>(likes.get(id));
    }

    @Override
    public void deleteLike(long id, long userId) {
        Set<Long> userLikes = likes.get(id);
        if (userLikes != null) {
            userLikes.remove(userId);
        }
    }

    @Override
    public Collection<Film> mostPopularFilms(long countLikes) {
        List<Map.Entry<Long, Integer>> filmLikeList = likes.entrySet()
                .stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(),
                        entry.getValue().size())).sorted((entry1, entry2) -> Integer.compare(entry2.getValue(),
                        entry1.getValue())).collect(Collectors.toList());

        List<Film> topFilms = new ArrayList<>();
        for (int i = 0; i < Math.min(countLikes, filmLikeList.size()); i++) {
            Long filmId = filmLikeList.get(i).getKey();
            Film film = films.get(filmId);
            if (film != null) {
                topFilms.add(film);
            }
        }
        return topFilms;
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

        Optional.ofNullable(film.getDuration()).ifPresent(oldFilm::setDuration);
        Optional.ofNullable(film.getReleaseDate()).ifPresent(oldFilm::setReleaseDate);
        Optional.ofNullable(film.getName()).ifPresent(oldFilm::setName);
        Optional.ofNullable(film.getDescription()).ifPresent(oldFilm::setDescription);

        return oldFilm;
    }
}
