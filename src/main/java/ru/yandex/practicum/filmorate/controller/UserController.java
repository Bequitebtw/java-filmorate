package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Collection<UserDto> getUsers() {
        return userService.getUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<UserDto> getUserFriends(@PathVariable long id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<UserDto> getMutualFriends(@PathVariable long id, @PathVariable int otherId) {
        return userService.getMutualFriends(id, otherId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public UserDto createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest);
    }

    @PutMapping
    public UserDto updateUserById(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUserById(updateUserRequest);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUserById(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public UserDto sendFriendRequest(@PathVariable int userId, @PathVariable int friendId) {
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public UserDto deleteFriend(@PathVariable int userId, @PathVariable int friendId) {
        return userService.deleteFriend(userId, friendId);
    }
}
