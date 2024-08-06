package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAnnotation;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.maker.Update;
import java.time.LocalDate;


@Data
public class Film {
    @NotNull(message = "Для того чтобы обновить данные нужено значение ID", groups = Update.class)
    private Long id;
    @NotNull(message = "Филь должен быть с названием", groups = Create.class)
    @NotBlank
    private String name;
    @Length(max = 200, message = "Длинна сообщения не может быть больше 200 символов")
    private String description;
    @ReleaseDateAnnotation
    @NotNull(groups = Create.class)
    private LocalDate releaseDate;
    @Min(value = 0, message = "Длительность фильма не может быть меньше 0")
    private Integer duration;

}
