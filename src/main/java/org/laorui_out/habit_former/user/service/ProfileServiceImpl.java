package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl extends ServiceImpl<UserMapper, UserBean> implements ProfileService {

    public UserBean getProfile(int userID) {
        return baseMapper.getUserProfileThroughID(userID);
    }

    public UserBean getProfile(String username) {
        return baseMapper.getUserProfileThroughUsername(username);
    }

    public boolean updatePassword(int userID, String password) {
        if(password==null || password.isEmpty()){
            return false;
        }else{
            return baseMapper.updatePassword(userID, password) > 0;
        }
    }

    public boolean updateUserName(int userID, String username) {
        if(username==null || username.isEmpty()){
            return false;
        }else{
            return baseMapper.updateUserName(userID, username) > 0;
        }
    }

    public boolean updateGender(int userID, String gender){
        return baseMapper.updateUserGender(userID, gender) > 0;
    }

    public boolean updateAddress(int userID, String address){
        return baseMapper.updateUserAddress(userID, address) > 0;
    }

    public boolean updateUserIntro(int userID, String userIntro){
        return baseMapper.updateUserIntro(userID, userIntro) > 0;
    }

    public boolean updateUserIcon(int userID, String userIcon) {
        if(userIcon==null || userIcon.isEmpty()){
            return false;
        }else{
            return baseMapper.updateUserIcon(userID, userIcon) > 0;
        }
    }
}

