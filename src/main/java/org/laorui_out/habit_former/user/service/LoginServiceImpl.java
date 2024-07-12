package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, UserBean> implements LoginService {
    public LoginResult login(String username, String password) {
        if(username.isEmpty() || password.length() < 8) {
            return LoginResult.INVALID_INPUT;
        }
        UserBean user = baseMapper.selectByUsername(username);
        if(user == null) {
            return LoginResult.USERNAME_ERROR;
        }
        if(user.getPassword().equals(password)) {
            return LoginResult.SUCCESS;
        }
        return LoginResult.PASSWORD_ERROR;
    }
}
