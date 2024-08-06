package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.data.DataMap;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.maker.Update;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static long filmId = 0L;
    static DataMap dataMap = new DataMap();

    @GetMapping
    public Collection<Film> allFilms() {
        return new ArrayList<>(dataMap.getFilms().values());
    }

    @PostMapping
    public Film create(@RequestBody @Validated(Create.class) Film film) {
        log.info("Попытка создать новый объект Film");
        film.setId(++filmId);
        dataMap.getFilms().put(film.getId(), film);

        log.info("Новый фильм с ID {} создан", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Validated(Update.class) Film film) {
        log.info("Обновляем данные фильма с ID: {}", film.getId());

        if (!dataMap.getFilms().containsKey(film.getId())) {
            throw new NotFoundException(film.getId());
        }

        Film oldFilm = dataMap.getFilms().get(film.getId());

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

        log.info("Данные о фильме с ID: {} обновлены", film.getId());

        return oldFilm;
    }
}
