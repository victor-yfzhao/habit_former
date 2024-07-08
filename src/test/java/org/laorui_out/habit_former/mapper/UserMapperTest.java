package org.laorui_out.habit_former.mapper;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laorui_out.habit_former.bean.UserBean;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Resource
    UserMapper mapper;

    @BeforeEach
    void setUp() {
        UserBean user = new UserBean();

        user.setUsername("test");
        user.setPassword("12345678");
        user.setUserIcon("default_icon");
        user.setCreateDate(new Date(System.currentTimeMillis()));

        mapper.insert(user);
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

    @Test
    void getUserProfile() {
        UserBean expected = mapper.selectByUsername("test");
        expected.setPassword(null);

        UserBean user = mapper.getUserProfileThroughID(mapper.selectByUsername("test").getUserID());
        assertEquals(expected, user);
    }
}