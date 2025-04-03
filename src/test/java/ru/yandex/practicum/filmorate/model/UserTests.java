package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

// Не знаю какие еще можно написать тесты для pojo, тесты в postman`е покрывают всю валидацию в контроллерах и классах
public class UserTests {
    private User user;

    @BeforeEach
    public void createNewUser() {
        user = new User("login", "email", LocalDate.of(2004, 12, 12));
    }

    @Test
    void shouldCreateUserWithValidData() {
        assertNull(user.getName());
        assertNull(user.getId());
        assertNotNull(user.getLogin());
        assertNotNull(user.getEmail());
        assertNotNull(user.getBirthday());
    }

    @Test
    void shouldSetUserCorrectFields() {
        user.setName("Name1");
        user.setEmail("Email1");
        user.setBirthday(LocalDate.of(1990, 5, 10));
        user.setId(1L);
        user.setLogin("Login1");
        assertEquals(user.getName(), "Name1");
        assertEquals(user.getEmail(), "Email1");
        assertEquals(user.getBirthday(), LocalDate.of(1990, 5, 10));
        assertEquals(user.getId(), 1);
        assertEquals(user.getLogin(), "Login1");
    }

}
