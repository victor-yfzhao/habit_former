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
class LoginServiceTest {

    @Resource
    LoginService loginService;

    @Resource
    RegisterService registerService;

    @Resource
    UserMapper mapper;

    @BeforeEach
    void setUp() {
        registerService.register("test", "Password");
        registerService.register("test1", "Password");
    }

    @AfterEach
    void tearDown() {
        UserBean[] users = mapper.selectAllUsers();
        for (UserBean user : users) {
            mapper.deleteUser(user.getUserID());
        }
    }

    @Test
    void login_correctly() {
        LoginResult result = loginService.login("test", "Password");
        assertEquals(LoginResult.SUCCESS, result);
    }

    @Test
    void login_invalid_input() {
        LoginResult result = loginService.login("", "Password");
        assertEquals(LoginResult.INVALID_INPUT, result);

        result = loginService.login("test", "");
        assertEquals(LoginResult.INVALID_INPUT, result);
    }

    @Test
    void login_username_not_exist() {
        LoginResult result = loginService.login("test2", "Password");
        assertEquals(LoginResult.USERNAME_ERROR, result);
    }

    @Test
    void login_password_error() {
        LoginResult result = loginService.login("test", "Password1");
        assertEquals(LoginResult.PASSWORD_ERROR, result);
    }
}