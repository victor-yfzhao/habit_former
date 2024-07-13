package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.UserBean;

public interface ProfileService extends IService<UserBean> {

    UserBean getProfile(int userID);
    UserBean getProfile(String username);

    boolean updateUserIcon(int userID, String userIcon);

    boolean updatePassword(int userID, String password);

    UserBean updateUsername(int userID, String newUsername);

    boolean updateGender(int userID, String gender);
    boolean updateAddress(int userID, String address);
    boolean updateUserIntro(int userID, String userIntro);
}
