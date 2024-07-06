package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.UserBean;

public interface ProfileService extends IService<UserBean> {

    UserBean getProfile(int userID);
    UserBean getProfile(String username);

    UserBean updateIcon(int userID, String icon);

    boolean updatePassword(int userID, String password);

    UserBean updateUsername(int userID, String newUsername);
}
