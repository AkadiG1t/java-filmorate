package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.InMemoryFilmRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository = new InMemoryFilmRepository();


    public Collection<Film> getAllFilms() {
        return filmRepository.getAll();
    }

    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    public Film updateFilm(Film film) {
        return filmRepository.update(film);
    }
}
