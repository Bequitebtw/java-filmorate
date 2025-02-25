package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    public Film likeFilm(int filmId, int userId) {
        if (inMemoryUserStorage.getUserById(userId).getFilms().contains(filmId)) {
            throw new ValidationException("Этот фильм вы уже лайкали");
        }
        inMemoryUserStorage.getUserById(userId).getFilms().add(filmId);
        inMemoryFilmStorage.getFilmById(filmId).setLike();
        return inMemoryFilmStorage.getFilmById(filmId);
    }

    public Film deleteLike(int filmId, int userId) {
        inMemoryUserStorage.getUserById(userId).getFilms().remove(filmId);
        inMemoryFilmStorage.getFilmById(filmId).deleteLike();
        return inMemoryFilmStorage.getFilmById(filmId);
    }

    public List<Film> getPopularFilms(int count) {
        return inMemoryFilmStorage.getFilms().stream()
                .sorted(Comparator.comparingLong(Film::getLikes).reversed())
                .limit(count)
                .toList();
    }

    public Collection<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }
}
