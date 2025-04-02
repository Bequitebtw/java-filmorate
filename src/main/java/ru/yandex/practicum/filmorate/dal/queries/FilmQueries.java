package ru.yandex.practicum.filmorate.dal.queries;

public class FilmQueries {
    public static final String FIND_ALL_QUERY = "SELECT f.id, f.name, f.description, f.duration, f.release_date, f.restriction_id, " +
            "r.restriction_type, g.id AS genre_id, g.name AS genre_name, " +
            "COUNT(DISTINCT uf.user_id) AS likes " +
            "FROM films AS f " +
            "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
            "LEFT JOIN genre g ON fg.genre_id = g.id " +
            "LEFT JOIN user_films AS uf ON f.id = uf.film_id " +
            "LEFT JOIN restriction r ON f.restriction_id = r.id " +
            "GROUP BY f.id, f.name, f.description, f.duration, f.release_date, " +
            "f.restriction_id, r.restriction_type, g.id, g.name " +
            "ORDER BY f.id;";
    public static final String FIND_POPULAR =
            "SELECT f.id, " +
                    "f.name, " +
                    "f.description, " +
                    "f.duration, " +
                    "f.release_date, " +
                    "f.restriction_id, " +
                    "r.restriction_type, " +
                    "g.id AS genre_id, " +
                    "g.name AS genre_name, " +
                    "COUNT(DISTINCT uf.user_id) AS likes " +
                    "FROM films f " +
                    "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
                    "LEFT JOIN genre g ON fg.genre_id = g.id " +
                    "LEFT JOIN user_films AS uf ON f.id = uf.film_id " +
                    "LEFT JOIN restriction r ON f.restriction_id = r.id " +
                    "GROUP BY f.id, f.name, f.description, f.duration, f.release_date, " +
                    "f.restriction_id, r.restriction_type, g.id, g.name " +
                    "ORDER BY likes DESC " +
                    "LIMIT ?;";

    public static final String FIND_BY_ID_QUERY =
            "SELECT f.id, " +
                    "f.name, " +
                    "f.description, " +
                    "f.duration, " +
                    "f.release_date, " +
                    "f.restriction_id, " +
                    "r.restriction_type, " +
                    "g.id AS genre_id, " +
                    "g.name AS genre_name, " +
                    "COUNT(DISTINCT uf.user_id) AS likes " +
                    "FROM films AS f " +
                    "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
                    "LEFT JOIN genre g ON fg.genre_id = g.id " +
                    "LEFT JOIN user_films AS uf ON f.id = uf.film_id " +
                    "LEFT JOIN restriction r ON f.restriction_id = r.id " +
                    "WHERE f.id = ? " +
                    "GROUP BY f.id, f.name, f.description, f.duration, f.release_date, " +
                    "f.restriction_id, r.restriction_type, g.id, g.name " +
                    "ORDER BY f.id " +
                    "LIMIT 1;";

    public static final String FIND_USER_FILMS =
            "SELECT f.id, " +
                    "f.name, " +
                    "f.description, " +
                    "f.duration, " +
                    "f.release_date, " +
                    "f.restriction_id, " +
                    "r.restriction_type, " +
                    "g.id AS genre_id, " +
                    "g.name AS genre_name, " +
                    "COUNT(DISTINCT uf.user_id) AS likes " +
                    "FROM films f " +
                    "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
                    "LEFT JOIN genre g ON fg.genre_id = g.id " +
                    "LEFT JOIN user_films AS uf ON f.id = uf.film_id " +
                    "LEFT JOIN restriction r ON f.restriction_id = r.id " +
                    "WHERE uf.user_id = ? " +
                    "GROUP BY f.id, f.name, f.description, f.duration, f.release_date, " +
                    "r.restriction_type " +
                    "ORDER BY f.id;";


    public static final String LIKE_FILM_QUERY = "INSERT INTO user_films (user_id,film_id) VALUES (?,?)";

    public static final String CREATE_FILM_QUERY = "INSERT INTO films (name,description,release_date,duration,restriction_id)" +
            " VALUES(?,?,?,?,?)";
    public static final String DELETE_FILMS_GENRE_BY_FILM_QUERY = "DELETE FROM FILM_GENRES WHERE film_id = ?";
    public static final String INSERT_FILMS_GENRES = "INSERT INTO FILM_GENRES VALUES(?,?)";
    public static final String UPDATE_FILM_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ? ,restriction_id = ? WHERE id = ?";

    public static final String DELETE_LIKE_FILM_QUERY = "DELETE FROM USER_FILMS WHERE film_id = ? AND user_id = ?";
    public static final String DELETE_FILM_QUERY = "DELETE FROM films WHERE id = ?";
}
