package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
   private final FilmService filmService;

   @GetMapping("/{id}")
   public Film get(@PathVariable long id) {
        return filmService.get(id);
   }

   @GetMapping
   public Collection<Film> getAllFilms() {
       return filmService.getAll();
   }

   @PostMapping
   public Film create(@RequestBody @Valid @Validated(Create.class) Film film) {

       return filmService.create(film);
   }

   @PutMapping
   public Film update(@RequestBody @Valid @Validated(Create.class) Film film) {

       return filmService.update(film);
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
   public Collection<Film> getMostPopularFilms(@RequestParam(name = "count", required = false) int count) {
       return filmService.mostPopularFilms(count);
   }

}
