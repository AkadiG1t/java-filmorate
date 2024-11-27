# java-filmorate
Template repository for Filmorate project.
Схема БД.png
Таблица Users:
Хранит все данные о пользователях. Каждый столбец содержит только одно значение.
Таблица Film:
Хранит информацию о фильмах с уникальным идентификатором для каждого фильма.
Таблица Likes:
Участвует в связи "многие-ко-многим" между пользователями и фильмами. Каждый пользователь может ставить лайк многим фильмам и каждый фильм может иметь лайки от многих пользователей.
Таблица Friends:
Используется для моделирования связи "друг-другу" между пользователями. Каждый пользователь может иметь много друзей, и каждый из них может быть другом множества других пользователей.
Примеры запросов:
  Все пользователи:
    SELECT * FROM Film;
  Все фильмы:
    SELECT * FROM Users;
  Топ N наиболее популярных фильмов:
  
  SELECT f.id, f.name, COUNT(l.user_id) AS like_count
    FROM Film f
    LEFT JOIN Likes l ON f.id = l.film_id
    GROUP BY f.id, f.name
    ORDER BY like_count DESC
    LIMIT N;  -- где N - количество популярных фильмов
  Список общих друзей с другим пользователем:
    SELECT f1.friend_id
      FROM Friends f1
      JOIN Friends f2 ON f1.friend_id = f2.friend_id
      WHERE f1.user_id = ? AND f2.user_id = ?;  -- ID пользователей
