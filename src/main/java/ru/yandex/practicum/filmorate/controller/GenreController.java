package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private  final FilmService filmService;

    @GetMapping
    public Collection<Genre> getAllGenres() {
        log.info("get запрос /genres");
        return filmService.getAllGenre();
    }

    @GetMapping("/{id}")
    public Optional<Genre> getGenre(@PathVariable Long id) {
        log.info("get /genres/{}", id);
        return filmService.getGenreById(id);
    }
}
