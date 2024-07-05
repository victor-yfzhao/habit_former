package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl extends ServiceImpl<UserMapper, UserBean> implements RegisterService {
    public int register(String username, String password) {
        if (username.isEmpty() || password.length() < 8){
            return -1;
        }
        try{
            baseMapper.insertUser(username, password);
        }catch (Exception e){
            return -2;
        }
        return 1;
    }
}
