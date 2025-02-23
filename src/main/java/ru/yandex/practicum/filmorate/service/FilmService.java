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

    public Film likeFilm(long filmId, long userId) {
        if (inMemoryUserStorage.getUserById(userId).getFilms().contains(filmId)) {
            throw new ValidationException("Этот фильм вы уже лайкали");
        }
        inMemoryUserStorage.getUserById(userId).getFilms().add(filmId);
        inMemoryFilmStorage.getFilmById(filmId).setLike();
        return inMemoryFilmStorage.getFilmById(filmId);
    }

    public Film deleteLike(long filmId, long userId) {
        inMemoryUserStorage.getUserById(userId).getFilms().remove(filmId);
        inMemoryFilmStorage.getFilmById(filmId).deleteLike();
        return inMemoryFilmStorage.getFilmById(filmId);
    }

    //    public Collection<Film> getPopularTreeFilms(long count) {
//        Iterator<Film> iterator = inMemoryFilmStorage.getPopularFilms().iterator();
//        List<Film> list = inMemoryFilmStorage.getPopularFilms();
//        long counter = 0;
//        List<Film> popularFilms = new ArrayList<>();
//        while (iterator.hasNext() && counter < count) {
//            counter++;
//            popularFilms.add(iterator.next());
//        }
//        return popularFilms;
//    }
    public List<Film> getPopularFilms(long count) {
        return inMemoryFilmStorage.getFilms().stream()
                .sorted(Comparator.comparingLong(Film::getLikes).reversed()) // Сортировка по лайкам
                .limit(count) // Берем только N фильмов
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
