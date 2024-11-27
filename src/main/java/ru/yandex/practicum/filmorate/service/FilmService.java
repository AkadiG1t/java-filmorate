package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.InMemoryFilmRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FilmService {
    private final FilmRepository filmRepository = new InMemoryFilmRepository();
    private final UserService userService = new UserService();


    public void putLike(long id, long userId) {
        if (filmRepository.get(id).isEmpty()) {
            throw new NotFoundException("Такого фильма нет в списке");
        }

        if (userService.get(userId) == null) {
            throw new ValidateException("Такого пользователя не существует");
        }

        if (id > 0 && userId > 0) {
            if (!filmRepository.get(id).get().getLikes().contains(userId)) {
                    filmRepository.get(id).get().getLikes().add(userId);
            } else {
                throw new ValidateException("Нельзя поставить лайк одному фильму более одного раза");
            }
        }
    }

    public void deleteLike(long id, long userId) {
        if (filmRepository.get(id).isEmpty()) {
            throw new NotFoundException("Такого фильма нет в списке");
        }
        if (!getUserId(id).contains(userId)) {
            throw new NotFoundException("Этот пользователь не ставил лайк этому фильму");
        }

        Set<Long> userLikes = filmRepository.get(id).get().getLikes();
        if (userLikes != null) {
            userLikes.remove(userId);
        }
    }


    public Collection<Film> mostPopularFilms(Long count) {
            if (count == null || count <= 0) {
                count = 10L;
            }

            List<Film> allFilms = (List<Film>) filmRepository.getAll();

            List<Film> topFilms = allFilms.stream()
                    .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                    .limit(count)
                    .collect(Collectors.toList());

            return topFilms;
        }

    public Set<Long> getUserId(long id) {
        return new HashSet<>(filmRepository.get(id).get().getLikes());
    }


    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }


    public Film create(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidateException("Имя не может быть пустым");
        }
        return filmRepository.save(film);
    }


    public Film update(Film film) {
        return filmRepository.update(film);
    }


    public Film get(Long id) {
        return filmRepository.get(id).orElseThrow(() -> new NotFoundException(id));
    }
}
