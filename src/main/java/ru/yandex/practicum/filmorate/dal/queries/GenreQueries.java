package ru.yandex.practicum.filmorate.dal.queries;

public class GenreQueries {
    public static final String FIND_ALL_GENRES = "SELECT * FROM GENRE";

    public static final String FIND_GENRE_BY_ID = "SELECT * FROM GENRE WHERE id = ?";
    public static final String GET_GENRES_BY_FILM_QUERY = "SELECT DISTINCT * FROM genre AS g JOIN film_genres AS fg ON g.id = fg.genre_id WHERE fg.film_id = ?";
}
