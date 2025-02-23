package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage inMemoryUserStorage;

    //не знаю что возвращать, возвращаю добавляемого друга
    public User addFriend(int firstUserId, int secondUserId) {
        if (firstUserId == secondUserId) {
            throw new ValidationException("Нельзя добавить самого себя в друзья");
        }
        inMemoryUserStorage.getUserById(firstUserId).getFriends().add(secondUserId);
        inMemoryUserStorage.getUserById(secondUserId).getFriends().add(firstUserId);
        return inMemoryUserStorage.getUserById(secondUserId);
    }

    public User deleteFriend(int firstUserId, int secondUserId) {
        inMemoryUserStorage.getUserById(firstUserId).getFriends().remove(secondUserId);
        inMemoryUserStorage.getUserById(secondUserId).getFriends().remove(firstUserId);
        return inMemoryUserStorage.getUserById(secondUserId);
    }

    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public Collection<User> getUserFriends(int id) {
        return inMemoryUserStorage.getUserById(id)
                .getFriends()
                .stream()
                .map(inMemoryUserStorage::getUserById)
                .toList();
    }

    public Collection<User> getMutualFriends(int id, int otherId) {
        Set<Integer> firstUserFriends = inMemoryUserStorage.getUserById(id).getFriends();
        Set<Integer> secondUserFriends = inMemoryUserStorage.getUserById(otherId).getFriends();
        List<Integer> intersectFriends = firstUserFriends.stream().filter(secondUserFriends::contains).toList();
        return intersectFriends.stream().map(inMemoryUserStorage::getUserById).toList();
    }

    public User createUser(User user) {
        return inMemoryUserStorage.createUser(user);
    }


    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

}

