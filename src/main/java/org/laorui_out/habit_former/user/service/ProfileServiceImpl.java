package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl extends ServiceImpl<UserMapper, UserBean> implements ProfileService {

    @Resource
    UserMapper userMapper;

    public UserBean getProfile(int userID) {
        return baseMapper.getUserProfileThroughID(userID);
    }

    public UserBean getProfile(String username) {
        return baseMapper.getUserProfileThroughUsername(username);
    }

//    public UserBean updateIcon(int userID, String icon) {
//        UserBean user = baseMapper.selectById(userID);
//        user.setUserIcon(icon);
//        baseMapper.updateUser(user);
//
//        // 确保userIcon字段已被更新
//        return baseMapper.getUserProfileThroughID(userID);
//    }

    public boolean updatePassword(int userID, String password) {
        UserBean user = baseMapper.getUserProfileThroughID(userID);
        user.setPassword(password);
        return baseMapper.updateUser(user) == 1;
    }

    public UserBean updateUsername(int userID, String username) {
        UserBean user = baseMapper.selectById(userID);

        user.setUsername(username);
        try{
            baseMapper.updateUser(user);
        }catch (Exception ignored){}

        return baseMapper.getUserProfileThroughID(userID);
    }

    public boolean updateGender(int userID, String gender){
        UserBean user = baseMapper.getUserProfileThroughID(userID);
        user.setGender(gender);
        return baseMapper.updateUser(user) == 1;
    }

    public boolean updateAddress(int userID, String address){
        UserBean user = baseMapper.getUserProfileThroughID(userID);
        user.setAddress(address);
        return baseMapper.updateUser(user) == 1;
    }

    public boolean updateUserIntro(int userID, String userIntro){
        UserBean user = baseMapper.getUserProfileThroughID(userID);
        user.setUserIntro(userIntro);
        return baseMapper.updateUser(user) == 1;
    }










    @Transactional
    public boolean updateUserIcon(int userID, String userIcon) {
        return userMapper.updateUserIcon(userID, userIcon) > 0;
    }


}
