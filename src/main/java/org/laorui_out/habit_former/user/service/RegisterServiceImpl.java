package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class RegisterServiceImpl extends ServiceImpl<UserMapper, UserBean> implements RegisterService {

    public RegisterResult register(String username, String password) {
        if (username.isEmpty() || password.length() < 8 || password.length() > 20) {
            return RegisterResult.INVALID_INPUT;
        }

        UserBean user = baseMapper.selectByUsername(username);

        if (user != null) {
            return RegisterResult.USERNAME_ALREADY_EXISTS;
        }

        try{
            UserBean new_user = new UserBean();

            new_user.setUsername(username);
            new_user.setPassword(password);
            new_user.setUserIcon("default_icon");
            new_user.setUserCreateDate(new Date(System.currentTimeMillis()));

            baseMapper.createUser(new_user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return RegisterResult.SQL_ERROR;
        }
        return RegisterResult.SUCCESS;
    }
}
