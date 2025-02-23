package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundFilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    HashMap<Long, Film> films = new HashMap<>();
    // все нормально работает, но непонятно почему компаратор не сортирует по лайкам, зато сортирует по всем оставшимся полям
//    TreeSet<Film> popularFilms = new TreeSet<>(Comparator.comparing(Film::getLikes).thenComparing(Film::getId).reversed());

    public Collection<Film> getFilms() {
        return films.values();
    }

//    public List<Film> getPopularFilms() {
//        return popularFilms.stream().toList();
//    }

    public Film createFilm(Film film) {
        film.setId(getNextId());
        films.put(getNextId(), film);
//        popularFilms.add(film);
        log.info("{} добавлен!", film);
        return film;
    }

    public Film getFilmById(long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new NotFoundFilmException(id);
    }

    public Film updateFilm(Film film) {
        if (film.getId() == null) {
            log.warn("Название фильме не может быть пустым");
            throw new ValidationException("Название фильме не может быть пустым");
        }
        if (!films.containsKey(film.getId())) {
            log.warn("Фильма с id {} не существует", film.getId());
            throw new NotFoundFilmException(film.getId());
        }
        Film oldFilm = films.get(film.getId());
        oldFilm.setId(film.getId());
        oldFilm.setName(film.getName());
        oldFilm.setDescription(film.getDescription());
        oldFilm.setReleaseDate(film.getReleaseDate());
        oldFilm.setDuration(film.getDuration());
        // по логике нужно удалять фильм у всех пользователей и ставить 0 лайков, так как это уже не тот фильм что был
        oldFilm.setLikes(film.getLikes());
        log.info("{} обновлен!", film);
        return oldFilm;
    }


    private long getNextId() {
        return films.keySet().stream().mapToLong(Long::longValue).max().orElse(0) + 1;
    }
}
