package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundGenreException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;


    public List<Genre> getAllGenres() {
        return genreRepository.findAllGenres();
    }

    public Genre getGenreById(long genreId) {
        return genreRepository.findGenreById(genreId).orElseThrow(() -> new NotFoundGenreException(genreId));
    }
}
