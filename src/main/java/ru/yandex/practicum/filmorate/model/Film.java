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

    public void setId(@NotNull(groups = Update.class) Long id) {
        this.id = id;
    }

    public void setName(@NotBlank(groups = Create.class) String name) {
        this.name = name;
    }

    public void setDescription(@Length(max = 200) String description) {
        this.description = description;
    }

    public void setReleaseDate(@NotNull(groups = Create.class) LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(@Min(value = 0) Integer duration) {
        this.duration = duration;
    }

    public @NotNull(groups = Update.class) Long getId() {
        return id;
    }

    public @NotBlank(groups = Create.class) String getName() {
        return name;
    }

    public @Length(max = 200) String getDescription() {
        return description;
    }

    public @NotNull(groups = Create.class) LocalDate getReleaseDate() {
        return releaseDate;
    }

    public @Min(value = 0) Integer getDuration() {
        return duration;
    }
}
