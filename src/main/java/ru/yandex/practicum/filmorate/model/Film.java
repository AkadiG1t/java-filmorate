package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.annotations.ReleaseDateAnnotation;
import ru.yandex.practicum.filmorate.marker.Create;
import ru.yandex.practicum.filmorate.marker.Update;
import java.time.LocalDate;
import java.util.LinkedHashSet;



@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Film {

    @NotNull(groups = Update.class)
    long id;
    @NotBlank(groups = Create.class)
    String name;
    String description;
    @ReleaseDateAnnotation
    @NotNull(groups = Create.class)
    LocalDate releaseDate;
    @Min(value = 0)
    Integer duration;
    final LinkedHashSet<Genre> genres = new LinkedHashSet<>();
    Mpa mpa;
}
