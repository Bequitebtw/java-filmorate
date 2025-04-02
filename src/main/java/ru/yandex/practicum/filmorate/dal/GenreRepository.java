package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.queries.GenreQueries;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository extends BaseRepository<Genre> {


    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public List<Genre> findAllGenres() {
        return findMany(GenreQueries.FIND_ALL_GENRES);
    }

    public Optional<Genre> findGenreById(long genreId) {
        return findOne(GenreQueries.FIND_GENRE_BY_ID, genreId);
    }

    public List<Genre> findGenresByFilmId(long filmId) {
        return findMany(GenreQueries.GET_GENRES_BY_FILM_QUERY, filmId);
    }
}
