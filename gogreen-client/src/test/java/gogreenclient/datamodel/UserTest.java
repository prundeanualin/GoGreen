package gogreenclient.datamodel;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void getUsername() {
        user.setUsername("Alin");
        assertEquals("Alin", user.getUsername());
    }

    @Test
    public void getPassword() {
        user.setPassword("alin");
        assertEquals("alin", user.getPassword());
    }

    @Test
    public void getBdate() {
        LocalDate date = LocalDate.of(1996, 07, 10);
        user.setBdate(date);
        assertEquals(date, user.getBdate());
    }

    @Test
    public void getNationality() {
        user.setEmail("Romanian");
        assertEquals("Romanian", user.getEmail());
    }

}