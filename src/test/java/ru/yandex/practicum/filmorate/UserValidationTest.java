package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {
    @Test
    public void testValidUser() {
        User user = new User(null, "valid.email@example.com", "validLogin", "Valid Name", "01.01.2000");
        assertDoesNotThrow(user::validate);
    }

    @Test
    public void testInvalidEmail() {
        User user = new User(null, "invalidemail", "validLogin", "Valid Name", "01.01.2000");
        Exception exception = assertThrows(ValidateException.class, user::validate);
        assertEquals("Неверный формат Email", exception.getMessage());
    }

    @Test
    public void testEmptyLogin() {
        User user = new User(null, "valid.email@example.com", "", "Valid Name", "01.01.2000");
        Exception exception = assertThrows(ValidateException.class, user::validate);
        assertEquals("Неверный формат Логина", exception.getMessage());
    }

    @Test
    public void testLoginWithSpaces() {
        User user = new User(null, "valid.email@example.com", "invalid login", "Valid Name", "01.01.2000");
        Exception exception = assertThrows(ValidateException.class, user::validate);
        assertEquals("Неверный формат Логина", exception.getMessage());
    }

    @Test
    public void testFutureBirthday() {
        User user = new User(null, "valid.email@example.com", "validLogin", "Valid Name", "01.01.2100");
        Exception exception = assertThrows(ValidateException.class, user::validate);
        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    public void testEmptyRequest() {
        User user = new User(null, null, null, null, null);
        Exception exception = assertThrows(ValidateException.class, user::validate);
        assertEquals("Неверный формат Email", exception.getMessage());
    }
}

