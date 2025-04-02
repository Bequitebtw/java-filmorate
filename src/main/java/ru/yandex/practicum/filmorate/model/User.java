package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class User {
    private Long id;
    private String name;
    @NotBlank(message = "Логин не может быть пустым или содержать пробелы")
    @NonNull
    private String login;
    @NonNull
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Почта не соответствует критериям")
    private String email;
    @Past
    @NonNull
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();
    private final Set<Integer> films = new HashSet<>();
}
