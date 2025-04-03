package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class NewUserRequest {
    @NonNull
    @NotBlank
    private String name;
    @NonNull
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Почта не соответствует критериям")
    private String email;
    @NonNull
    @NotBlank(message = "Логин не может быть пустым или содержать пробелы")
    private String login;
    @NonNull
    private LocalDate birthday;
    private Mpa mpa = new Mpa();
    private List<Genre> genres = new ArrayList<>();
}
