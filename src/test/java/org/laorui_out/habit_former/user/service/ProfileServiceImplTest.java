package org.laorui_out.habit_former.user.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceImplTest {

    @Resource
    ProfileService profileService;

    @Resource
    RegisterService registerService;

    @Resource
    UserMapper mapper;

    UserBean expectedUser = new UserBean(0, "test", null, "default_icon");

    @BeforeEach
    void setUp() {
        registerService.register("test", "password");
        expectedUser.setUserID(mapper.selectByUsername("test").getUserID());
    }

    @AfterEach
    void tearDown() {
        UserBean[] users = mapper.selectAllUsers();
        for (UserBean user : users) {
            mapper.deleteUser(user.getUserID());
        }
    }

    @Test
    void getProfile() {
        UserBean user = profileService.getProfile(expectedUser.getUserID());

        assertEquals(expectedUser, user);
    }

    @Test
    void updateIcon() {
        UserBean user = profileService.updateIcon(expectedUser.getUserID(), "new_icon");
        assertEquals("new_icon", user.getUserIcon());
    }

    @Test
    void updatePassword() {
        boolean result = profileService.updatePassword(expectedUser.getUserID(), "new_password");
        assertTrue(result);

        UserBean user = mapper.selectById(expectedUser.getUserID());
        assertEquals("new_password", user.getPassword());
    }

    @Test
    void updateUsername() {
        registerService.register("test1", "password");

        UserBean user = profileService.updateUsername(expectedUser.getUserID(), "new_username");
        assertEquals("new_username", user.getUsername());

        user = profileService.updateUsername(expectedUser.getUserID(), "test1");
        assertEquals("new_username", user.getUsername());
    }
}