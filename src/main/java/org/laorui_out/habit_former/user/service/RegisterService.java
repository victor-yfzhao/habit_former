package org.laorui_out.habit_former.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.UserBean;

public interface RegisterService extends IService<UserBean> {
    RegisterResult register(String username, String password);
}
