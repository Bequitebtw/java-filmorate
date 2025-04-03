package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    HashMap<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public Optional<User> createUser(User user) {
        return null;
    }

    public Optional<User> updateUser(User user) {
        if (user.getId() == null) {
            log.warn("Id должен быть указан");
            throw new ValidationException("Id должен быть указан");
        }
        if (!users.containsKey(user.getId())) {
            log.warn("Пользователя с id {} не существует", user.getId());
            throw new NotFoundUserException(user.getId());
        }
        nameValidation(user);
        User oldUser = users.get(user.getId());
        oldUser.setLogin(user.getLogin());
        oldUser.setName(user.getName());
        oldUser.setBirthday(user.getBirthday());
        oldUser.setEmail(user.getEmail());
        log.info("{} обновил данные!", oldUser);
        return Optional.of(oldUser);
    }

    public Optional<User> getUserById(long id) {
        if (users.containsKey(id)) {
            return Optional.of(users.get(id));
        }
        throw new NotFoundUserException(id);
    }

    private Integer getNextId() {
        return users.keySet().stream().mapToInt(Long::intValue).max().orElse(0) + 1;
    }

    private void nameValidation(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
