package org.laorui_out.habit_former.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Resource
    RegisterService registerService;

//
//    @Resource
//    UserMapper userMapper;
//
//    @GetMapping("/users")
//    public List<UserBean> users() {
//        return userMapper.selectList(null);
//    }

//    @GetMapping("/login")
//    public Object login(String username, String password) {
//
//    }

    @PostMapping("/register")
    public Object register(String username, String password) {
        return registerService.register(username, password);
    }
}
