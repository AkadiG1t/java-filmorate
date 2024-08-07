package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
   private final FilmService filmService = new FilmService();

    @GetMapping
    public Collection<Film> allFilms() {
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        log.info("Попытка создать новый объект Film");
        Film createdFilm = filmService.createFilm(film);
        log.info("Новый фильм с ID {} создан", film.getId());

        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        log.info("Обновляем данные фильма с ID: {}", film.getId());
        Film updatedFilm = filmService.updateFilm(film);
        log.info("Данные о фильме с ID: {} обновлены", film.getId());

        return updatedFilm;
    }
}
