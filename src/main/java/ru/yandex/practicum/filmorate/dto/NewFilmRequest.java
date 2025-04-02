package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.annotation.FutureAfter;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class NewFilmRequest {
    @NonNull
    @NotBlank
    private String name;
    @Size(max = 200, message = "Описание не может быть больше 200 символов")
    @NonNull
    private String description;
    @NonNull
    @FutureAfter(minDate = "1895-12-28")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    @NonNull
    private Long duration;
    private Mpa mpa;
    private List<Genre> genres;
}
