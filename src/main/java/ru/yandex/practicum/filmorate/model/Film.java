package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.yandex.practicum.filmorate.annotation.FutureAfter;

import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Film {
    private Long id;
    @NotEmpty(message = "Название не может быть пустым")
    @NonNull
    private String name;
    @Size(max = 200, message = "Описание не может быть больше 200 символов")
    @NonNull
    private String description;
    @FutureAfter(minDate = "1895-12-28")
    @NonNull
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    @NonNull
    private Long duration;
    private Long likes = 0L;
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();

}
