package ru.yandex.practicum.filmorate;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmServiceImpl;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FilmValidationTest {

    Validator validator;
    private FilmServiceImpl filmService;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidFilm() {
        Film film = new Film();
        film.setName("Название");
        film.setDescription("Какое-то описание");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertEquals(0, violations.size());
    }

    @Test
    public void testFilmMissingName() {
        Film film = new Film();
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(120);

        assertThrows(NullPointerException.class, () -> {
            filmService.create(film);
        });


    }

    @Test
    public void testFilmInvalidReleaseDate() {
        Film film = new Film();
        film.setName("название");
        film.setDescription("описание");
        film.setReleaseDate(null);
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film, Create.class);

        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("releaseDate", violation.getPropertyPath().toString());
    }

    @Test
    public void testFilmNegativeDuration() {
        Film film = new Film();
        film.setName("Название");
        film.setDescription("описание");
        film.setReleaseDate(LocalDate.of(2022, 1, 1));
        film.setDuration(-120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertEquals(1, violations.size());
        ConstraintViolation<Film> violation = violations.iterator().next();
        assertEquals("duration", violation.getPropertyPath().toString());
    }
}