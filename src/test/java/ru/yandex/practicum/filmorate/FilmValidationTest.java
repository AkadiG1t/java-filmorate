package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;

public class FilmValidationTest {

    @Test
    public void testValidFilm() {
        Film film = new Film(null, "Valid Name", "Valid Description", "28.12.1985", 120);
        assertDoesNotThrow(film::validate);
    }

    @Test
    public void testEmptyName() {
        Film film = new Film(null, "", "Valid Description", "28.12.1985", 120);
        Exception exception = assertThrows(ValidateException.class, film::validate);
        assertEquals("Название фильма не может быть пустым", exception.getMessage());
    }

    @Test
    public void testLongDescription() {
        String longDescription = "a".repeat(201);
        Film film = new Film(null, "Valid Name", longDescription, "28.12.1985", 120);
        Exception exception = assertThrows(ValidateException.class, film::validate);
        assertEquals("Слишком длинное описание", exception.getMessage());
    }

    @Test
    public void testInvalidDate() {
        Film film = new Film(null, "Valid Name", "Valid Description", "27.12.1985", 120);
        Exception exception = assertThrows(ValidateException.class, film::validate);
        assertEquals("Дата не может быть до: 28.12.1985", exception.getMessage());
    }

    @Test
    public void testNegativeDuration() {
        Film film = new Film(null, "Valid Name", "Valid Description", "28.12.1985", -1);
        Exception exception = assertThrows(ValidateException.class, film::validate);
        assertEquals("Продолжительность фильма не может быть меньше 0", exception.getMessage());
    }

    @Test
    public void testEmptyRequest() {
        Film film = new Film(null, null, null, null, null);
        Exception exception = assertThrows(ValidateException.class, film::validate);
        assertEquals("Название фильма не может быть пустым", exception.getMessage());
    }
}
