package org.laorui_out.habit_former.user.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.laorui_out.habit_former.user.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterServiceImplTest {

    @Autowired
    RegisterService registerService;

    @Resource
    UserMapper mapper;

    @BeforeEach
    void setUp() {

        UserBean user = new UserBean();
        user.setUsername("test1");
        user.setPassword("12345678");

        mapper.insertUser(user.getUsername(), user.getPassword());
    }

    @AfterEach
    void tearDown() {
        UserBean[] users = mapper.selectAllUsers();
        for (UserBean user : users) {
            mapper.deleteUser(user.getUserID());
        }
    }

    @Test
    void register_correct_access() {
        int result = registerService.register("test3", "12345678");
        assertEquals(1, result);
    }

    @Test
    void register_null_username(){
        int result = registerService.register("", "12345678");
        assertEquals(-1, result);
    }

    @Test
    void register_short_password(){
        int result = registerService.register("test3", "123");
        assertEquals(-1, result);
    }

    @Test
    void register_exist_username(){
        int result = registerService.register("test1", "12345678");
        assertEquals(-2, result);
    }
}