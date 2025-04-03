package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Не знаю какие еще можно написать тесты для pojo, тесты в postman`е покрывают всю валидацию в контроллерах и классах
public class FilmTests {

    private Film film;
    private ArrayList<String> genres = new ArrayList<>();

    @BeforeAll
    public static void setGenres() {

    }

    @BeforeEach
    public void createNewFilm() {
        genres.add("Horror");
        genres.add("Comedy");
        film = new Film("name", "desk", LocalDate.of(1900, 12, 12), 200L);
    }

    @Test
    void shouldCreateFilmWithValidData() {
        assertNull(film.getId());
        assertNotNull(film.getName());
        assertNotNull(film.getDescription());
        assertNotNull(film.getReleaseDate());
        assertNotNull(film.getDuration());
    }

    @Test
    void shouldSetFilmCorrectFields() {
        film.setName("Name1");
        film.setDescription("Desk1");
        film.setReleaseDate(LocalDate.of(2004, 5, 10));
        film.setId(1L);
        film.setDuration(100L);
        assertEquals(film.getName(), "Name1");
        assertEquals(film.getDescription(), "Desk1");
        assertEquals(film.getReleaseDate(), LocalDate.of(2004, 5, 10));
        assertEquals(film.getId(), 1);
        assertEquals(film.getDuration(), 100L);
    }

}
