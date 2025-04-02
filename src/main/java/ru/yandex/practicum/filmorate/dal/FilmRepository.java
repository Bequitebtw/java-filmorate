package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.dal.queries.FilmQueries;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.Optional;


@Repository
public class FilmRepository extends BaseRepository<Film> implements FilmStorage {
    private final GenreRepository genreRepository = new GenreRepository(jdbc, new GenreRowMapper());

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public List<Film> findAll() {
        List<Film> films = findMany(FilmQueries.FIND_ALL_QUERY);

        for (Film film : films) {
            List<Genre> genres = genreRepository.findGenresByFilmId(film.getId());
            film.setGenres(genres);
        }
        return films;
    }

    public Optional<Film> findFilmById(long filmId) {

        Optional<Film> film = findOne(FilmQueries.FIND_BY_ID_QUERY, filmId);

        film.ifPresent((film1 -> {
            List<Genre> genres = genreRepository.findGenresByFilmId(filmId);
            film.get().setGenres(genres);
        }));
        return film;
    }

    public List<Film> findPopularFilms(long count) {
        List<Film> films = findMany(FilmQueries.FIND_POPULAR, count);
        for (Film film : films) {
            List<Genre> genres = genreRepository.findGenresByFilmId(film.getId());
            film.setGenres(genres);
        }
        return films;

    }

    public List<Film> findUserFilms(long userId) {
        return findMany(FilmQueries.FIND_USER_FILMS, userId);
    }

    public void addLikeFilm(long userId, long filmId) {
        jdbc.update(FilmQueries.LIKE_FILM_QUERY, userId, filmId);
    }

    public Optional<Film> createFilm(Film film) {
        List<Genre> genres = film.getGenres();// Преобразуем в Set для удаления дубликатов
        Optional<Long> id = insertAndGetId(FilmQueries.CREATE_FILM_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        if (genres != null || !film.getGenres().isEmpty()) {
            for (Genre genre : genres) {
                jdbc.update(FilmQueries.INSERT_FILMS_GENRES, id.get(), genre.getId());
            }
        }
        id.ifPresent(film::setId);
        return Optional.of(film);
    }

    public Optional<Film> updateFilm(Film film) {
        int rowsUpdated = jdbc.update(FilmQueries.UPDATE_FILM_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        List<Genre> genres = film.getGenres();
        if (genres != null) {
            jdbc.update(FilmQueries.DELETE_FILMS_GENRE_BY_FILM_QUERY, film.getId());
            if (!genres.isEmpty()) {
                for (Genre genre : genres) {
                    jdbc.update(FilmQueries.INSERT_FILMS_GENRES, film.getId(), genre.getId());
                }
            }
        }

        if (rowsUpdated > 0) {
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

    public void deleteLikeFilm(long filmId, long userId) {
        jdbc.update(FilmQueries.DELETE_LIKE_FILM_QUERY, filmId, userId);
    }

    public Optional<Film> deleteFilm(long filmId) {
        Optional<Film> film = findFilmById(filmId);
        int rowUpdated = jdbc.update(FilmQueries.DELETE_FILM_QUERY, filmId);
        if (rowUpdated > 0) {
            return film;
        }
        return Optional.empty();
    }
}