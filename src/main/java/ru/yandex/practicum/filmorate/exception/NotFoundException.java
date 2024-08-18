package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Объект с таким ID не найден " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
