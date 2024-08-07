package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAnnotation;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.maker.Update;
import java.time.LocalDate;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    @NotNull(groups = Update.class)
    Long id;
    @NotBlank(groups = Create.class)
    String name;
    @Length(max = 200, message = "Длинна сообщения не может быть больше 200 символов")
    String description;
    @ReleaseDateAnnotation
    @NotNull(groups = Create.class)
    LocalDate releaseDate;
    @Min(value = 0, message = "Длительность фильма не может быть меньше 0")
    Integer duration;

}
