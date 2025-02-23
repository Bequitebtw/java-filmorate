package ru.yandex.practicum.filmorate.exception;

public class NotFoundUserException extends RuntimeException {
    private final long id;

    public NotFoundUserException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Пользователь с id: " + id + " не найден";
    }
}
