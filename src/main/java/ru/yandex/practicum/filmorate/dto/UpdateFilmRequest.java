package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.annotation.FutureAfter;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class UpdateFilmRequest {
    @NonNull
    private Long id;
    private String name;
    private String description;
    private Long duration;
    @FutureAfter(minDate = "1895-12-28")
    @Past
    private LocalDate releaseDate;
    private Mpa mpa;
    private List<Genre> genres;

    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    public boolean hasDescription() {
        return !(description == null || description.isBlank());
    }

    public boolean hasDuration() {
        return !(duration == null);
    }

    public boolean hasReleaseDate() {
        return !(releaseDate == null);
    }

    public boolean hasRestrictionType() {
        return !(mpa == null);
    }

    public boolean hasGenres() {
        return !(genres == null);
    }
}