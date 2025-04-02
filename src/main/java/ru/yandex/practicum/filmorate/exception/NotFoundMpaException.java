package ru.yandex.practicum.filmorate.exception;

public class NotFoundMpaException extends RuntimeException {
    private final long id;

    public NotFoundMpaException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Ограничение с id: " + id + " не найдено";
    }
}
