package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Optional<Film> createFilm(Film film);

    Optional<Film> updateFilm(Film film);

    Collection<Film> findAll();

    Optional<Film> findFilmById(long id);
}
