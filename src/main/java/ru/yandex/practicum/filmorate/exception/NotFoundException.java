package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Фильм не найден")
public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Объект с таким ID не найден " + id);
    }
}
