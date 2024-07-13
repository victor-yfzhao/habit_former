package org.laorui_out.habit_former.user.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.PosterPictureBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.bean.UserPersonalPageBean;
import org.laorui_out.habit_former.poster.service.PosterPictureService;
import org.laorui_out.habit_former.poster.service.PosterService;
import org.laorui_out.habit_former.user.service.*;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    @Resource
    PosterPictureService posterPictureService;
    @Resource
    PosterService posterService;

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
    public ResponseMessage<Object> getUserPersonalPage(int userID) {
        // TODO : 此处仍需要获取用户帖子
        try{
            List<PosterBean> posterBeanList = posterPictureService.getPosterWithPicturesByUserID(userID);
            System.out.println(posterBeanList);
            UserBean user = profileService.getProfile(userID);
            if (user != null){
                List<Object> posterMessages = new ArrayList<>();
                if(posterBeanList!=null && !posterBeanList.isEmpty()){
                    for(PosterBean posterBean : posterBeanList){
                        Object posterBeanPartItem = posterService.getPosterParts(posterBean.getPosterID());
                        posterMessages.add(posterBeanPartItem);
                    }
                }
                UserPersonalPageBean userPersonalPageBean = new UserPersonalPageBean(
                        user.getUserID(),
                        user.getUsername(),
                        user.getUserIcon(),
                        posterMessages
                );
                return new ResponseMessage<>(200,"用户个人展示成功",userPersonalPageBean);
            }else{
                return new ResponseMessage<>(400, "不存在对应的用户信息", null);
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,"展示用户个人界面失败",e.getMessage());
        }

    }

    //更新用户头像
    @PutMapping("/user/update_icon")
    public ResponseMessage<String> updateUserIcon(@RequestParam Integer userID, @RequestParam String userIcon) {
        boolean isUpdated = profileService.updateUserIcon(userID, userIcon);
        if (isUpdated) {
            return new ResponseMessage<>(200,"成功","用户头像更换成功");
        } else {
            return new ResponseMessage<>(500, "失败", "用户头像更新失败");
        }
    }

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

