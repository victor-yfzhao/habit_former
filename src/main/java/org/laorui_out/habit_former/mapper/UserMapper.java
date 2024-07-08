package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.UserBean;

@Mapper
public interface UserMapper extends BaseMapper<UserBean> {

    // 根据用户名查找用户（仅供登录使用）
    @Select("select * " +
            "from User " +
            "where username = #{username}")
    UserBean selectByUsername(String username);

    // 删除用户
    @Delete("delete from User " +
            "where userID = #{userID}")
    int deleteUser(int userID);

    // 更新用户
    @Update("update User " +
            "set username = #{username}, password = #{password}, userIcon = #{userIcon} " +
            "where userID = #{userID}")
    int updateUser(UserBean userBean);

    // 返回所有用户（供admin使用）
    @Select("select *" +
            "from User")
    @Results({
            @Result(property = "userID", column = "userID"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "userIcon", column = "userIcon")
    })
    UserBean[] selectAllUsers();

    // 根据用户ID返回用户信息
    @Select("select userID, username, userIcon " +
            "from User " +
            "where userID = #{userID}")
    UserBean getUserProfileThroughID(int userID);

    // 根据用户名返回用户信息
    @Select("select userID, username, userIcon " +
            "from User " +
            "where username = #{username}")
    UserBean getUserProfileThroughUsername(String username);
}
