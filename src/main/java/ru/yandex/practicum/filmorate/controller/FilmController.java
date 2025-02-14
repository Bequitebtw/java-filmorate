package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        films.put(getNextId(), film);
        log.info("{} добавлен!", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (film.getId() == null) {
            log.warn("Название фильме не может быть пустым");
            throw new ValidationException("Название фильме не может быть пустым");
        }
        if (!films.containsKey(film.getId())) {
            log.warn("Фильма с id {} не существует", film.getId());
            throw new ValidationException("Фильма с таким id не существует");
        }
        Film oldFilm = films.get(film.getId());
        oldFilm.setId(film.getId());
        oldFilm.setName(film.getName());
        oldFilm.setDescription(film.getDescription());
        oldFilm.setReleaseDate(film.getReleaseDate());
        oldFilm.setDuration(film.getDuration());
        log.info("{} обновлен!", film);
        return oldFilm;
    }

    private int getNextId() {
        return films.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }
}
