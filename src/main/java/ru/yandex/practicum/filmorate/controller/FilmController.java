package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Slf4j
@RestController()
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Map<Long, Film> films = new HashMap<>();
    private final String date = "28.12.1985";

    @GetMapping
    @Override
    public Collection<Film> getAllObject() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    @Override
    public Film create(Film film) {

        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Пустое наименование фильма {}", film.getName());
            throw new ValidateException("Название фильма не может быть пустым");
        }

        if (film.getDescription().length() > 200) {
            log.error("Слишком длинное описание {}", film.getDescription().length());
            throw new ValidateException("Слишком длинное описание. " + film.getDescription().length() +
                    " Описание не может быть более 200 символов");
        }

        try {
            if (LocalDate.parse(date, dateTimeFormatter)
                    .isAfter(LocalDate.parse(film.getReleaseDate(), dateTimeFormatter))) {

                log.error("Указана некорректная дата {} при создании объекта Film", film.getReleaseDate());
                throw new ValidateException("Дата не может быть до: " + date);
            }
        } catch (DateTimeParseException e) {
            log.error("Ошибка при форматировании даты");
            throw new ValidateException("Возникла ошибка форматирования, проверьте, " +
                    "что дата выхода фильма соответствует: дд.мм.ггг");
        }

        if (film.getDuration() < 0) {
            log.error("Некорректная продолжительность фильма, пользователь указал значение меньше 0 {}",
                    film.getDuration());
            throw new ValidateException("Продолжительность фильма не может быть меньше 0");
        }

        film.setId(nextId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    @Override
    public Film update(Film film) {
        if (film.getId() == null) {
            log.error("id == null {}", (Object) null);
            throw new ValidateException("Чтобы обновить данные о фильме нужен его ID");

        }

        Optional<Film> optionalFilm = Optional.ofNullable(films.get(film.getId()));

        if (!films.containsKey(film.getId()) || optionalFilm.isEmpty()) {
            log.error("Не найден фильм с id {}", film.getId());
            throw new NotFoundException("Фильм с таким ID не найден");
        }

        Film oldFilm = optionalFilm.get();

        if (film.getName() != null) {
            oldFilm.setName(film.getName());
        }

        if (film.getDescription() != null) {
            if (film.getDescription().length() < 200) {
                oldFilm.setDescription(film.getDescription());
            } else {
                throw new ValidateException("Слишком длинное описание. " + film.getDescription().length() +
                        " Описание не может быть более 200 символов");
            }
        }

        if (film.getDuration() != null) {
            if (film.getDuration() > 0) {
                oldFilm.setDuration(film.getDuration());
            } else {
                log.error("Некорректная продолжительность фильма, при обновлении продолжительности " +
                        "пользователь указал знанчение меньше 0 {}", film.getDuration());
                throw new ValidateException("Продолжительность фильма не может быть меньше 0");
            }
        }

        if (film.getReleaseDate() != null) {
            try {
                if (LocalDate.parse(date, dateTimeFormatter)
                        .isAfter(LocalDate.parse(film.getReleaseDate(), dateTimeFormatter))) {
                    log.error("Указана некорректная дата {} при обновлении объекта Film", film.getReleaseDate());
                    throw new ValidateException("Дата выхода фильма не может быть до: " + date);
                } else {
                    oldFilm.setReleaseDate(film.getReleaseDate());
                }
            } catch (DateTimeParseException e) {
                log.error("Ошибка форматирования даты {} при попытке обновить объект Film", film.getReleaseDate());
                throw new ValidateException("Возникла ошибка форматирования, проверьте, " +
                        "что дата выхода фильма соответствует: дд.мм.ггг");
            }

        }
        return oldFilm;
    }

    @Override
    public Collection<Long> getUsersId() {
            return new ArrayList<>(films.keySet());
    }
}

