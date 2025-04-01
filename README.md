# DATABASE DIAGRAM
![Filmorate (1)](https://github.com/user-attachments/assets/645bf1e6-3430-49ad-ac63-7298c55dc6ef)
Таблица films - фильмы\
Таблица users - пользователи\
Таблица user_films - фильмы пользователей, связь многие ко многим\
Таблица user_friends - друзья пользователей, свьзь многие ко многим\
Таблица restriction - ограничения фильма, связь один ко многим\
Таблица films_genres - жанры фильма, свзяь многие ко многим\
Таблица genre - жанры, связь один ко многим\
Таблица friendship_status - статус дружбы, связь один ко многим
## ПРИМЕРЫ ЗАПРОСОВ 
Вывести всех пользователей
```sql
SELECT * FROM users
```
Вывести всех друзей пользователя
``` sql
SELECT u.* FROM users AS u
JOIN user_friends AS uf ON u.id = uf.user_id
JOIN friendship_status AS fr ON uf.FRIENDSHIP_STATUS_ID = fr.id
WHERE uf.friend_id = ? AND fr.status = "friends"
```
Найти фильм по id 
```sql
SELECT f.id, 
f.name, 
f.description,
f.duration,
f.release_date,
r.restriction_type,
COUNT(DISTINCT uf.user_id) AS likes 
FROM films AS f 
JOIN user_films AS uf ON f.id = uf.film_id 
JOIN restriction r ON f.restriction_id = r.id 
WHERE f.id = ? 
GROUP BY f.id
```
Популярные фильмы
```sql
SELECT f.id,
f.name,
f.DESCRIPTION, 
f.DURATION, 
f.RELEASE_DATE, r.RESTRICTION_TYPE, 
COUNT(DISTINCT uf.user_id) AS LIKES 
FROM films AS f 
JOIN user_films AS uf ON f.id = uf.film_id 
JOIN restriction r ON f.RESTRICTION_ID = r.ID 
GROUP BY f.id 
ORDER BY likes DESC 
LIMIT ?
```
