package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final MpaRepository mpaRepository;
    private final GenreRepository genreRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public Collection<FilmDto> getFilms() {
        return filmRepository.findAll().stream().map(FilmMapper::mapToFilmDto).collect(Collectors.toList());
    }

    public Collection<FilmDto> getPopularFilms(long count) {
        return filmRepository.findPopularFilms(count).stream().map(FilmMapper::mapToFilmDto).collect(Collectors.toList());
    }

    public FilmDto getFilmById(long filmId) {
        filmRepository.findFilmById(filmId).orElseThrow(() -> new NotFoundFilmException(filmId));
        return FilmMapper.mapToFilmDto(filmRepository.findFilmById(filmId)
                .orElseThrow(() -> new BadRequestException("Не удалось получить фильм")));
    }

    public List<FilmDto> getUserFilms(long userId) {
        return filmRepository.findUserFilms(userId).stream().map(FilmMapper::mapToFilmDto).collect(Collectors.toList());
    }

    public void likeFilm(long userId, long filmId) {
        userRepository.getUserById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        filmRepository.findFilmById(filmId).orElseThrow(() -> new NotFoundFilmException(filmId));
        filmRepository.addLikeFilm(userId, filmId);
    }

    public void deleteLike(long filmId, long userId) {
        userRepository.getUserById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        filmRepository.findFilmById(filmId).orElseThrow(() -> new NotFoundFilmException(filmId));
        filmRepository.deleteLikeFilm(filmId, userId);
    }

    public FilmDto createFilm(NewFilmRequest newFilmRequest) {
        Mpa mpa = newFilmRequest.getMpa();
        List<Genre> genres = newFilmRequest.getGenres();
        if (genres != null) {
            Set<Genre> deleteDuplicates = new HashSet<>(genres);
            genres.clear();
            genres.addAll(deleteDuplicates);
            for (Genre genre : genres) {
                genreRepository.findGenreById(genre.getId()).orElseThrow(() -> new NotFoundGenreException(genre.getId()));
            }
        }
        if (mpa != null) {
            mpaRepository.findMpaById(mpa.getId()).orElseThrow(() -> new NotFoundMpaException(mpa.getId()));
        }
        if (genres == null) {
            newFilmRequest.setGenres(new ArrayList<>());
        }

        return filmRepository.createFilm(FilmMapper.mapToFilm(newFilmRequest))
                .map(FilmMapper::mapToFilmDto)
                .orElseThrow(() -> new BadRequestException("Не удалось добавить фильм"));
    }

    public FilmDto updateFilm(UpdateFilmRequest request) {
        Film updateFilm = filmRepository.findFilmById(request.getId())
                .map(film -> FilmMapper.updateFilmFields(film, request))
                .orElseThrow(() -> new NotFoundFilmException(request.getId()));
        return filmRepository.updateFilm(updateFilm)
                .map(FilmMapper::mapToFilmDto)
                .orElseThrow(() -> new BadRequestException("Не удалось обновить фильм"));
    }

    public FilmDto deleteFilm(long filmId) {
        filmRepository.findFilmById(filmId).orElseThrow(() -> new NotFoundFilmException(filmId));
        return filmRepository.deleteFilm(filmId)
                .map(FilmMapper::mapToFilmDto)
                .orElseThrow(() -> new BadRequestException("Не удалось удалить фильм"));
    }

}
