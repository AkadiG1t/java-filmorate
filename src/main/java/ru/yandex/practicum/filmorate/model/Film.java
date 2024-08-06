package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exception.ValidateException;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    private String name;
    private String description;
    private String releaseDate;
    private Integer duration;



    public void validate() throws ValidateException {
        if (name == null || name.isEmpty()) {
            throw new ValidateException("Название фильма не может быть пустым");
        }
        if (description != null && description.length() > 200) {
            throw new ValidateException("Слишком длинное описание");
        }
        if (releaseDate != null && !releaseDate.equals("28.12.1985")) {
            throw new ValidateException("Дата не может быть до: 28.12.1985");
        }
        if (duration != null && duration < 0) {
            throw new ValidateException("Продолжительность фильма не может быть меньше 0");
        }
    }
}

