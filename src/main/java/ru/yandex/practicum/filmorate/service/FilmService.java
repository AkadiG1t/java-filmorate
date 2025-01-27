package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.GenreRepository;
import ru.yandex.practicum.filmorate.repository.LikeRepository;
import ru.yandex.practicum.filmorate.repository.MpaRepository;

import java.util.*;

@Slf4j
@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final UserService userService;
    private final GenreRepository genreRepository;
    private final LikeRepository likeRepository;
    private final MpaRepository mpaRepository;


    public FilmService(@Qualifier("filmDBStorage") FilmRepository filmRepository,
                       UserService userService,
                       GenreRepository genreRepository, LikeRepository likeRepository, MpaRepository mpaRepository) {
        this.filmRepository = filmRepository;
        this.userService = userService;
        this.genreRepository = genreRepository;
        this.likeRepository = likeRepository;
        this.mpaRepository = mpaRepository;
    }

    public void putLike(long id, long userId) {
        log.info("Пользователь с ID: {} пытается поставить лайк фильму с ID: {}", userId, id);

        if (userService.get(userId) == null) {
            throw  new NotFoundException("ПОльзователь с id " + userId + " не найлен");
        }

        if (filmRepository.get(id).isEmpty()) {
            throw new NotFoundException("Фильм с id " + id + "не найден");
        }

        likeRepository.addLike(id, userId);
    }

    public void deleteLike(long id, long userId) {
        log.info("Пользователь с ID: {} пытается удалить лайк у фильма с ID: {}", userId, id);

        if (userService.get(userId) == null) {
            throw  new NotFoundException("ПОльзователь с id " + userId + " не найлен");
        }

        if (filmRepository.get(id).isEmpty()) {
            throw new NotFoundException("Фильм с id " + id + "не найден");
        }

        likeRepository.removeLike(id, userId);
    }

    public Collection<Film> mostPopularFilms(int count) {
        List<Film> films = filmRepository.getMostPopular(count);
        genreRepository.findAllGenreByFilms(films);

        return films;
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

    public Collection<Mpa> getAllMpa() {
        return mpaRepository.getAllMpa();
    }

    public Optional<Mpa> getRatingById(long id) {
        return mpaRepository.getMpaById(id);
    }

    public Collection<Genre> getAllGenre() {
        return genreRepository.getAllGenre();
    }

    public Optional<Genre> getGenreById(long id) {
        return Optional.ofNullable(genreRepository.getGenreById(id).orElseThrow(() -> new NotFoundException(id)));
    }

}
