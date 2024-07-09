package org.laorui_out.habit_former.user.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.laorui_out.habit_former.bean.UserBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
//    }

    @PostMapping("/register")
    public Object register(String username, String password) {
        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password);

        try{
            userMapper.insert(user);
        }catch (Exception e){
            return "用户名已存在";
        }
        return "注册成功";
    }

}
