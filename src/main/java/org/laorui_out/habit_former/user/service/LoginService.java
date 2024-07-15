package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.laorui_out.habit_former.bean.UserBean;
import org.springframework.stereotype.Service;

public interface LoginService extends IService<UserBean> {
    LoginResult login (String username, String password, HttpServletRequest request);
}
