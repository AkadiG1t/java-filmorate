package ru.yandex.practicum.filmorate.data;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Data
public class DataMap {
    private final Map<Long, Film> films = new HashMap<>();
    private final Map<Long, User> users = new HashMap<>();
}
