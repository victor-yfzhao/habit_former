package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.UserBean;


public interface LoginService extends IService<UserBean> {
    LoginResult login (String username, String password);
}
