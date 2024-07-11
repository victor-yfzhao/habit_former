package org.laorui_out.habit_former.user.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.user.service.*;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@MapperScan("org.laorui_out.habit_former.mapper")
@RestController
public class UserController {
    @Resource
    LoginService loginService;

    @Resource
    RegisterService registerService;

    @Resource
    ProfileService profileService;

    @GetMapping("/login")
    public ResponseMessage<UserBean> login(String username, String password) {
        LoginResult result;
        try{
            result = loginService.login(username, password);
            if (Objects.requireNonNull(result) == LoginResult.SUCCESS) {
                return new ResponseMessage<>(200, result.toString(), profileService.getProfile(username));
            }}catch(Exception e){
            return new ResponseMessage<>(500, e.getMessage(), null);
        }
        return new ResponseMessage<>(400, result.toString(), null);
    }

    @PostMapping("/register")
    public ResponseMessage<RegisterResult> register(String username, String password) {
        RegisterResult result = registerService.register(username, password);
        if (Objects.requireNonNull(result) == RegisterResult.SUCCESS) {
            return new ResponseMessage<>(200, result.toString(), null);
        }
        return new ResponseMessage<>(400, result.toString(), null);
    }

    @GetMapping("/user")
    public ResponseMessage<UserBean> getUserPersonalPage(int userID) {
        // TODO : 此处仍需要获取用户帖子

        UserBean user = profileService.getProfile(userID);
        if (user != null)
            return new ResponseMessage<>(200, "success get profile", user);
        return new ResponseMessage<>(400, "failed to get profile", null);
    }

//    @PostMapping("/user/update_icon")
//    public ResponseMessage<UserBean> updateUserIcon(int userID, String icon) {
//        // TODO : 上传图片的处理
//    }

    @PostMapping("/user/update_password")
    public ResponseMessage<Boolean> updateUserPassword(int userID, String password) {
        if(profileService.updatePassword(userID, password)) {
            return new ResponseMessage<>(200, "success update password", true);
        }
        return new ResponseMessage<>(400, "failed to update password", false);
    }

    @PostMapping("/user/update_username")
    public ResponseMessage<UserBean> updateUserUsername(int userID, String username) {
        UserBean user = profileService.updateUsername(userID, username);
        if(user != null) {
            return new ResponseMessage<>(200, "success update username", user);
        }
        return new ResponseMessage<>(400, "failed to update username", null);
    }
}

