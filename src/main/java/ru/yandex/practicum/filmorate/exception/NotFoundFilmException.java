package ru.yandex.practicum.filmorate.exception;

public class NotFoundFilmException extends RuntimeException {
    private final long id;

    public NotFoundFilmException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Фильм с id: " + id + " не найден";
    }
}
