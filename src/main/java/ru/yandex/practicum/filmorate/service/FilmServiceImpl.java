package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.repository.InMemoryFilmRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final UserService userService;

    @Override
    public void putLike(long id, long userId) {
        if (filmRepository.get(id).isEmpty()) {
            throw new NotFoundException("Такого фильма нет в списке");
        }

        if (userService.get(userId) == null) {
            throw new ValidateException("Такого пользователя не существует");
        }

        filmRepository.putLike(id, userId);
    }

    @Override
    public void deleteLike(long id, long userId) {
        if (filmRepository.get(id).isEmpty()) {
            throw new ValidateException("Такого фильма нет в списке");
        }
        if (!filmRepository.getUserId(id).contains(userId)) {
            throw new ValidateException("Этот пользователь не ставил лайк этому фильму");
        }

        filmRepository.deleteLike(id, userId);
    }

    @Override
    public Collection<Film> mostPopularFilms(Long countLikes) {
        if (countLikes == null || countLikes == 0) {
            return filmRepository.mostPotularFilms();
        }

        return filmRepository.mostPopularFilms(countLikes);
    }


    @Override
    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }

    @Override
    public Film create(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidateException("Имя не может быть пустым");
        }
        return filmRepository.save(film);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.update(film);
    }

    @Override
    public Film get(Long id) {
        if (filmRepository.get(id).isEmpty()) {
          throw new NotFoundException(id);
        }
        return filmRepository.get(id).get();
    }
}
