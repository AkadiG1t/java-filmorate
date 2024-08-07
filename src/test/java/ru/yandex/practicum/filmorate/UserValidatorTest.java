package ru.yandex.practicum.filmorate;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.maker.Create;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {
    Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserValidation_validUser() {
        User user = new User();
        user.setEmail("asdzxc@azxcc.com");
        user.setLogin("zxcUser");
        user.setName("testUser");
        user.setBirthday(LocalDate.now());

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testUserValidation_missingEmail() {
        User user = new User();
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        user.setId(1L);

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertEquals(1, violations.size());
        assertEquals("Чтобы зарегестрироваться нужен email", violations.iterator().next().getMessage());
    }

    @Test
    public void testUserValidation_invalidEmail() {
        User user = new User();
        user.setEmail("invalid-email");
        user.setLogin("testuser");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must be a well-formed email address", violations.iterator().next().getMessage());
    }

    @Test
    public void testUserValidation_missingLogin() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testUserValidation_missingBirthday() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setName("Test User");

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }
}