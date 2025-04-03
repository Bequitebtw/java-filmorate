package ru.yandex.practicum.filmorate.dal;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.queries.UserQueries;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepository extends BaseRepository<User> implements UserStorage {

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public List<User> getUsers() {
        return findMany(UserQueries.FIND_ALL_QUERY);
    }

    public Optional<User> getUserById(long userId) {
        return findOne(UserQueries.FIND_BY_ID_QUERY, userId);
    }

    public List<User> findMutualFriends(long userId, long friendId) {
        return findMany(UserQueries.FIND_MUTUAL_FRIENDS_QUERY, userId, friendId, userId, friendId);
    }

    public List<User> findFriendsById(long userId) {
        return findMany(UserQueries.FIND_FIENDS_QUERY, userId, "friends");
    }

    public Optional<User> deleteUser(long id) {
        Optional<User> userDelete = getUserById(id);
        if (userDelete.isEmpty()) {
            throw new NotFoundUserException(id);
        }
        int rowsDeleted = jdbc.update(UserQueries.DELETE_USER_QUERY, id);
        if (rowsDeleted > 0) {
            return userDelete;
        }
        throw new BadRequestException("Не удалось удалить пользователя");
    }


    public Optional<User> updateUser(User user) {
        int rowsUpdated = jdbc.update(UserQueries.UPDATE_USER_QUERY, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        if (rowsUpdated > 0) {
            return Optional.of(user);
        }
        return Optional.empty();
    }


    public Optional<User> createUser(User user) {
        Optional<Long> id = insertAndGetId(UserQueries.ADD_USER_QUERY, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday());
        if (id.isPresent()) {
            user.setId(id.get());
            return Optional.of(user);
        }
        return Optional.empty();

    }


    public Optional<User> addFriend(long userId, long friendId) {
        jdbc.update(UserQueries.ADD_FRIEND_QUERY, friendId, userId, 1);
        return getUserById(friendId);

    }


    public Optional<User> deleteFriend(long userId, long friendId) {
        jdbc.update(UserQueries.DELETE_FRIEND_QUERY, friendId, userId);
        return getUserById(friendId);
    }

}