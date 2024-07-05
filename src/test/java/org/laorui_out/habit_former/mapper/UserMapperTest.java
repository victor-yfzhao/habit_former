package org.laorui_out.habit_former.mapper;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laorui_out.habit_former.bean.UserBean;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Resource
    UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper.insertUser("test", "12345678");
    }

    @AfterEach
    void tearDown() {
        UserBean[] users = mapper.selectAllUsers();
        for (UserBean user : users) {
            mapper.deleteUser(user.getUserID());
        }
    }

    @Test
    void selectByUsername() {
        UserBean user = mapper.selectByUsername("test");
        assertNotNull(user);
        assertEquals("test", user.getUsername());

        user = mapper.selectByUsername("test1");
        assertNull(user);
    }

    @Test
    void insertUser() {
        int result = mapper.insertUser("test1", "12345678");
        assertEquals(1, result);

        assertThrows(Exception.class, () -> mapper.insertUser("test1", "12345678"));
    }

    @Test
    void deleteUser() {
        int result = mapper.deleteUser(mapper.selectByUsername("test").getUserID());
        assertEquals(1, result);
        UserBean user = mapper.selectByUsername("test");
        assertNull(user);
    }

    @Test
    void updateUser() {
        UserBean user = mapper.selectByUsername("test");
        user.setPassword("87654321");
        int result = mapper.updateUser(user);
        assertEquals(1, result);
    }

    @Test
    void selectAllUsers() {
        UserBean[] users = mapper.selectAllUsers();
        assertEquals(1, users.length);
    }
}