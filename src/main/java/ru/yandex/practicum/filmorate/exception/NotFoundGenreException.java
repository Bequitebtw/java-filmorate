package ru.yandex.practicum.filmorate.exception;

public class NotFoundGenreException extends RuntimeException {
    private final long id;

    public NotFoundGenreException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Жанр с id: " + id + " не найден";
    }
}
