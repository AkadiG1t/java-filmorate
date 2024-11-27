package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.model.Film;

import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
   private final FilmService filmService = new FilmService();

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
       Film updatedFilm = filmService.update(film);

       return updatedFilm;
   }

   @PutMapping("{id}/like/{userId}")
   public ResponseEntity<Film> putLike(@PathVariable long id, @PathVariable long userId) {
       filmService.putLike(id, userId);

       return ResponseEntity.ok(filmService.get(id));
   }

   @DeleteMapping("/{id}/like/{userId}")
   public ResponseEntity<String> deleteLike(@PathVariable long id, @PathVariable long userId) {
       filmService.deleteLike(id, userId);

       return ResponseEntity.ok("Вы удалили лайк фильму с id " + id);
   }

   @GetMapping("/popular")
   @Validated
   public Collection<Film> getMostPopularFilms(@RequestParam(name = "count", required = false) Long count) {
       return filmService.mostPopularFilms(count);
   }

}
