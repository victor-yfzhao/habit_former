package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.catalina.User;
import org.laorui_out.habit_former.bean.UserBean;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserManageService extends IService<UserBean> {

    IPage<UserBean> selectAllUsers(Page<UserBean> page);

    List<UserBean> selectUserByID(int userID);

    List<UserBean> selectUserByUsername(String username);

    int deleteUser(int userID);

    int updateUser(UserBean userBean);

    int createUser(UserBean userBean);

}
