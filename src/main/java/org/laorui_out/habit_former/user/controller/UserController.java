package org.laorui_out.habit_former.user.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.user.service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Resource
    UserMapper userMapper;

    @GetMapping("/users")
    public List<UserBean> users() {
        return userMapper.selectList(null);
    }

//    @GetMapping("/login")
//    public Object login(String username, String password) {
//        UserBean user = userMapper.getUserByUsername(username);
//        if(user == null){
//            return "用户名不存在";
//        }else{
//            if(user.getPassword().equals(password)){
//                return "登录成功";
//            }else{
//                return "密码错误";
//            }
//        }
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
