package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl extends ServiceImpl<UserMapper, UserBean> implements ProfileService {

    public UserBean getProfile(int userID) {
        return baseMapper.getUserProfileThroughID(userID);
    }

    public UserBean getProfile(String username) {
        return baseMapper.getUserProfileThroughUsername(username);
    }

    public UserBean updateIcon(int userID, String icon) {
        UserBean user = baseMapper.selectById(userID);
        user.setUserIcon(icon);
        baseMapper.updateUser(user);

        // 确保userIcon字段已被更新
        return baseMapper.getUserProfileThroughID(userID);
    }

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
}
