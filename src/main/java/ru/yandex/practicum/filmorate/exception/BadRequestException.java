package ru.yandex.practicum.filmorate.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String error) {
        super(error);
    }

}
