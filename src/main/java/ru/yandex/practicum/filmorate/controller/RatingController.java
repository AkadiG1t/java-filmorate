package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.Collection;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")

public class RatingController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Mpa> getAllRating() {
        log.info("GET запрос /mpa");
        return filmService.getAllMpa();
    }

    @GetMapping("/{id}")
    public Optional<Mpa> getRating(@PathVariable Long id) {
        log.info("GET / mpa / {}", id);
        return filmService.getRatingById(id);
    }
}
