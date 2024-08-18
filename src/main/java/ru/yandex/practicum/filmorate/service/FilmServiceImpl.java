package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final UserService userService;

    @Override
    public void putLike(long id, long userId) {
        Film film = get(id);
        User user = userService.get(userId);

        filmRepository.putLike(id, userId);
    }

    @Override
    public void deleteLike(long id, long userId) {
        Film film = get(id);
        User user = userService.get(userId);

        filmRepository.deleteLike(id, userId);
    }

    @Override
    public Collection<Film> mostPopularFilms(Long countLikes) {
        if (countLikes == null || countLikes == 0) {
            return mostPotularFilms();
        }

        return mostPopularFilms(countLikes);
    }

    @Override
    public Collection<Film> mostPotularFilms() {
        return List.of();
    }

    @Override
    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }

    @Override
    public Film create(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.update(film);
    }

    @Override
    public Film get(Long id) {
        if (filmRepository.get(id).isEmpty()) {
          throw new NotFoundException(id);
        }
        return filmRepository.get(id).get();
    }
}