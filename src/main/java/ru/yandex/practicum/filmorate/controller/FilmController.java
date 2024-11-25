package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
   private final FilmService filmService = new FilmServiceImpl();
   private Logger log;

    @GetMapping("/{id}")
   public Film get(@PathVariable long id) {
        return filmService.get(id);
   }

   @GetMapping
   public Collection<Film> allFilms() {
       return filmService.getAll();
   }

   @PostMapping
   public Film create(@RequestBody @Valid @Validated(Create.class) Film film) {
       Film createdFilm = filmService.create(film);

       return film;
   }

   @PutMapping
   public Film update(@RequestBody @Valid @Validated(Create.class) Film film) {
       log.info("Обновляем данные фильма с ID: {}", film.getId());
       Film updatedFilm = filmService.update(film);
       log.info("Данные о фильме с ID: {} обновлены", film.getId());

       return updatedFilm;
   }

   @PutMapping("{id}/like/{userId}")
   public ResponseEntity<String> putLike(@PathVariable long id, @PathVariable long userId) {
       filmService.putLike(id, userId);

       return ResponseEntity.ok("Вы поставили лайк фильму с id " + id);
   }

   @DeleteMapping("/{id}/like/{userId}")
   public ResponseEntity<String> deleteLike(@PathVariable long id, @PathVariable long userId) {
       filmService.deleteLike(id, userId);

       return ResponseEntity.ok("Вы удалили лайк фильму с id " + id);
   }

   @GetMapping("/popular")
   public Collection<Film> getMostPopularFilms(@RequestParam(name = "count") Long countLikes) {
       return filmService.mostPopularFilms(countLikes);
   }
}
