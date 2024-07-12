package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManageServiceImpl extends ServiceImpl<UserMapper, UserBean> implements UserManageService {

    @Override
    public IPage<UserBean> selectAllUsers(int pageCount, int pageSize) {
        // TODO 分页查询
        return null;
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
        return baseMapper.updateUser(userBean);
    }

    @Override
    public int createUser(UserBean userBean) {
        baseMapper.createUser(userBean);
        return userBean.getUserID();
    }
}
