package ru.yandex.practicum.filmorate.controller;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public abstract class Controller <T>{
    public abstract Film create(Film film);
    public abstract T update(T t);
    public abstract Collection<T> getAllObject();
    public abstract Collection<Long> getUsersId();
    protected long nextId () {
        long nextId = getUsersId().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++nextId;
    }
}
