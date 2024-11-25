package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAnnotation;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.marker.Update;
import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Film {
    @NotNull(groups = Update.class)
    Long id;
    @NotBlank(groups = Create.class)
    String name;
    @Length(max = 200)
    String description;
    @ReleaseDateAnnotation
    @NotNull(groups = Create.class)
    LocalDate releaseDate;
    @Min(value = 0)
    Integer duration;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
