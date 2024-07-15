package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.laorui_out.habit_former.user.service.RegisterResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManageServiceImpl extends ServiceImpl<UserMapper, UserBean> implements UserManageService {

    @Override
    public IPage<UserBean> selectAllUsers(Page<UserBean> page) {
        return baseMapper.selectPage(page, null);
    }

    @Override
    public List<UserBean> selectUserByID(int userID) {
        UserBean user = baseMapper.selectById(userID);
        return List.of(user);
    }

    @Override
    public List<UserBean> selectUserByUsername(String username) {
        UserBean userBean = baseMapper.selectByUsername(username);
        return List.of(userBean);
    }

    @Override
    public int deleteUser(int userID) {
        return baseMapper.deleteUser(userID);
    }

    @Override
    public int updateUser(UserBean userBean) {
        try{
            return baseMapper.updateUser(userBean);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public RegisterResult createUser(UserBean userBean) {
        try{
            baseMapper.createUser(userBean);
            return RegisterResult.SUCCESS;
        }catch (Exception e){
            return RegisterResult.USERNAME_ALREADY_EXISTS;
        }
    }
}
