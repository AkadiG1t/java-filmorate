package ru.yandex.practicum.filmorate.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Объект с таким ID не найден " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
