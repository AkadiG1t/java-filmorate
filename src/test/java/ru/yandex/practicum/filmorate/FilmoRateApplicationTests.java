package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDBStorage.class})
public class FilmoRateApplicationTests {
    private final UserDBStorage userDBStorage;

    @Test
    public void testFindUserById() {
        User user = new User();
        user.setName("testName");
        user.setLogin("testLogin");
        user.setEmail("test@user.com");
        user.setBirthday(LocalDate.now());

        userDBStorage.save(user);

        Optional<User> userOptional = userDBStorage.get(user.getId());

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user1 ->
                        assertThat(user1).hasFieldOrPropertyWithValue("id", user.getId())
                );
    }

    @Test
    public void testUserSave() {
        User user = new User();
        user.setName("testName");
        user.setLogin("testLogin");
        user.setEmail("test@user.com");
        user.setBirthday(LocalDate.now());

        userDBStorage.save(user);

        Optional<User> userOptional = userDBStorage.get(user.getId());

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(saveUser -> {
                   assertThat(saveUser).hasFieldOrPropertyWithValue("id", user.getId());
                   assertThat(saveUser).hasFieldOrPropertyWithValue("name", user.getName());
                   assertThat(saveUser).hasFieldOrPropertyWithValue("email", user.getEmail());
                   assertThat(saveUser).hasFieldOrPropertyWithValue("birthday", user.getBirthday());
                   assertThat(saveUser).hasFieldOrPropertyWithValue("login", user.getLogin());
                });
    }

    @Test
    public void testFindAllUsers() {
        User user = new User();
        user.setName("testName");
        user.setLogin("testLogin");
        user.setEmail("test@user.com");
        user.setBirthday(LocalDate.now());

        User user1 = new User();
        user1.setName("test1Name");
        user1.setLogin("testLogin");
        user1.setEmail("test@user.com");
        user1.setBirthday(LocalDate.now());

        userDBStorage.save(user);
        userDBStorage.save(user1);

        List<User> users = (List<User>) userDBStorage.findAll();

        assertThat(users).isNotNull();
        assertThat(users.isEmpty()).isFalse();
    }


}
