DROP TABLE IF EXISTS user_films;
DROP TABLE IF EXISTS FILM_GENRES;
DROP TABLE IF EXISTS FILMS;
DROP TABLE IF EXISTS user_friends;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS restriction;
DROP TABLE IF EXISTS film_genres;
DROP TABLE IF EXISTS friendship_status;


CREATE TABLE IF NOT EXISTS friendship_status
(
    id     INT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS genre
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS restriction
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    restriction_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    name           VARCHAR(255) NOT NULL,
    description    TEXT NOT NULL,
    release_date   DATE NOT NULL,
    duration       INT NOT NULL,
    restriction_id INT,
    FOREIGN KEY (restriction_id) REFERENCES restriction (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(255),
    login    VARCHAR(255) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_films
(
    user_id INT,
    film_id INT,
    PRIMARY KEY (user_id, film_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_friends
(
    user_id              INT,
    friend_id            INT,
    friendship_status_id INT,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (friendship_status_id) REFERENCES friendship_status (id)
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  INT,
    genre_id INT,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genre (id) ON DELETE CASCADE
);
