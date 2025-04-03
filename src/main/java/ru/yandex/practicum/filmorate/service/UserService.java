package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUsers() {
        return userRepository.getUsers()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(int id) {
        userRepository.getUserById(id).orElseThrow(() -> new NotFoundUserException(id));
        return UserMapper.mapToUserDto(userRepository.getUserById(id).get());
    }

    public List<UserDto> getUserFriends(long id) {
        userRepository.getUserById(id).orElseThrow(() -> new NotFoundUserException(id));
        return userRepository.findFriendsById(id)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getMutualFriends(long id, int otherId) {
        if (id == otherId) {
            throw new IllegalArgumentException("Нельзя передавать одинаковый id");
        }
        return userRepository.findMutualFriends(id, otherId)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = UserMapper.mapToUser(newUserRequest);
        return userRepository.createUser(user)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new BadRequestException("Не удалось создать пользователя"));
    }

    public UserDto updateUserById(UpdateUserRequest request) {
        User updatedUser = userRepository.getUserById(request.getId())
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundUserException(request.getId()));
        return userRepository.updateUser(updatedUser)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new BadRequestException("Не удалось обновить пользователя"));
    }

    public UserDto deleteUser(long userId) {
        return userRepository.deleteUser(userId)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new BadRequestException("Не удалось удалить пользователя"));
    }

    public UserDto addFriend(int userId, int friendId) {
        if (userId == friendId) {
            throw new IllegalArgumentException("Нельзя добавить самого себя в друзья");
        }
        userRepository.getUserById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        userRepository.getUserById(friendId).orElseThrow(() -> new NotFoundUserException(friendId));
        return userRepository.addFriend(userId, friendId).map(UserMapper::mapToUserDto).get();
    }

    public UserDto deleteFriend(int userId, int friendId) {
        userRepository.getUserById(friendId)
                .orElseThrow(() -> new NotFoundUserException(friendId));
        userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundUserException(userId));

        return userRepository.deleteFriend(userId, friendId).map(UserMapper::mapToUserDto).get();
    }

}

