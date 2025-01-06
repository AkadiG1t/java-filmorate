Untitled.png

Таблица user  
Содержит информацию о пользователях:
- id: уникальный идентификатор пользователя (PRIMARY KEY).
- email: адрес электронной почты пользователя.
- login: логин, используемый пользователем для авторизации.
- name: имя пользователя (можно быть пустым).
- birthday: дата рождения пользователя.

Таблица film  
Содержит информацию о фильмах:
- id: уникальный идентификатор фильма (PRIMARY KEY).
- name: название фильма.
- description: краткое описание фильма.
- release_date: дата выпуска фильма.
- genre: жанр фильма.
- MPA: рейтинг фильма по классификации MPA.
- duration: длительность фильма в минутах.

Таблица friends  
Реализует взаимосвязь между пользователями:
- user_id: идентификатор пользователя (ссылка на пользователя в таблице user).
- friend_id: идентификатор его друга (также ссылка на пользователя в таблице user).

Таблица film_like  
Используется для хранения лайков пользователей к фильмам:
- user_id: идентификатор пользователя, который поставил лайк (ссылка на таблицу user).
- film_id: идентификатор фильма, которому поставлен лайк (ссылка на таблицу film). 
