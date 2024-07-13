package org.laorui_out.habit_former.user.controller;

import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
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

    //展示用户个人信息以及它发过的所有帖子
    @GetMapping("/user")
    public ResponseMessage<UserPersonalPageBean> getUserPersonalPage(int userID) {
        try{
            List<PosterBean> posterBeanList = posterPictureService.getPosterWithPicturesByUserID(userID);
            return getObjectResponseMessage(userID, posterBeanList);
        }catch (Exception e){
            return new ResponseMessage<>(500,e.getMessage(),null);
        }
    }

    //展示用户个人信息以及它收藏de的所有帖子
    @GetMapping("/user/collection")
    public ResponseMessage<UserPersonalPageBean> getUserPersonalCollectionPage(int userID) {
        try{
            List<PosterBean> posterBeanList = posterPictureService.getPosterCollectionWithPicturesByUserID(userID);
            return getObjectResponseMessage(userID, posterBeanList);
        }catch (Exception e){
            return new ResponseMessage<>(500,e.getMessage(),null);
        }
    }

    @NotNull
    private ResponseMessage<UserPersonalPageBean> getObjectResponseMessage(int userID, List<PosterBean> posterBeanList) {
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
                    user.getGender(),
                    user.getAddress(),
                    user.getUserIntro(),
                    posterMessages
            );
            return new ResponseMessage<>(200,"用户个人展示成功",userPersonalPageBean);
        }else{
            return new ResponseMessage<>(400, "不存在对应的用户信息", null);
        }
    }


    //更新用户头像
    @PostMapping("/user/update_icon")
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
    public ResponseMessage<Boolean> updateUserUserName(int userID, String username) {
        if(profileService.updateUserName(userID, username)) {
            return new ResponseMessage<>(200, "success update username", true);
        }
        return new ResponseMessage<>(400, "failed to update username", false);
    }

    @PostMapping("/user/update_gender")
    public ResponseMessage<Boolean> updateUserGender(int userID, String gender) {
        if(profileService.updateGender(userID, gender)) {
            return new ResponseMessage<>(200, "success update gender", true);
        }
        return new ResponseMessage<>(400, "failed to update gender", false);
    }

    @PostMapping("/user/update_address")
    public ResponseMessage<Boolean> updateUserAddress(int userID, String address) {
        if(profileService.updateAddress(userID, address)) {
            return new ResponseMessage<>(200, "success update address", true);
        }
        return new ResponseMessage<>(400, "failed to update address", false);
    }

    @PostMapping("/user/update_userIntro")
    public ResponseMessage<Boolean> updateUserIntro(int userID, String userIntro) {
        if(profileService.updateUserIntro(userID, userIntro)) {
            return new ResponseMessage<>(200, "success update userIntro", true);
        }
        return new ResponseMessage<>(400, "failed to update userIntro", false);
    }


}
