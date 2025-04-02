package ru.yandex.practicum.filmorate.dal.queries;

public class UserQueries {
    public static final String FIND_ALL_QUERY = "SELECT * FROM users";
    public static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    public static final String FIND_FIENDS_QUERY = "SELECT u.* FROM users AS u " +
            "JOIN user_friends AS uf ON u.id = uf.user_id " +
            "JOIN friendship_status AS fr ON uf.FRIENDSHIP_STATUS_ID = fr.id " +
            "WHERE uf.friend_id = ? AND fr.status = ?";
    public static final String FIND_MUTUAL_FRIENDS_QUERY =
            "SELECT u.* " +
                    "FROM users u " +
                    "JOIN ( " +
                    "SELECT DISTINCT f.friend_id " +
                    "FROM ( " +
                    "SELECT uf.friend_id FROM user_friends uf WHERE uf.user_id = ? " +
                    "UNION " +
                    "SELECT uf.user_id FROM user_friends uf WHERE uf.friend_id = ? " +
                    ") f " +
                    "JOIN ( " +
                    "SELECT uf.friend_id FROM user_friends uf WHERE uf.user_id = ? " +
                    "UNION " +
                    "SELECT uf.user_id FROM user_friends uf WHERE uf.friend_id = ? " +
                    ") f2 ON f.friend_id = f2.friend_id " +
                    ") common_friends ON u.id = common_friends.friend_id;";


    public static final String ADD_USER_QUERY = "INSERT INTO users (name, login, email, birthday) VALUES (?, ?, ?, ?)";

    public static final String UPDATE_USER_QUERY = "UPDATE users SET name = ?, login = ?, email = ?, birthday = ? WHERE id = ?";

    public static final String DELETE_USER_QUERY = "DELETE from users WHERE id = ?";

    public static final String ADD_FRIEND_QUERY = "INSERT INTO user_friends (user_id,friend_id,friendship_status_id) VALUES (?,?,?);";
    public static final String DELETE_FRIEND_QUERY = "DELETE FROM user_friends WHERE " +
            "user_id = ? AND friend_id = ?";
}
