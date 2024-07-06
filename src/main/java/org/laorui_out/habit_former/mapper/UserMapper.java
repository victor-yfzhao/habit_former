package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.UserBean;

@Mapper
public interface UserMapper extends BaseMapper<UserBean> {
    @Select("select * " +
            "from User " +
            "where username = #{username}")
    UserBean selectByUsername(String username);


    @Insert("insert into User (username, password, userIcon) " +
            "values (#{username}, #{password}, 'default_icon')")
    int insertUser(String username, String password);


    @Delete("delete from User " +
            "where userID = #{userID}")
    int deleteUser(int userID);


    @Update("update User " +
            "set username = #{username}, password = #{password}, userIcon = #{userIcon} " +
            "where userID = #{userID}")
    int updateUser(UserBean userBean);


    @Select("select *" +
            "from User")
    @Results({
            @Result(property = "userID", column = "userID"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "userIcon", column = "userIcon")
    })
    UserBean[] selectAllUsers();


    @Select("select userID, username, userIcon " +
            "from User " +
            "where userID = #{userID}")
    UserBean getUserProfile(int userID);

    @Select("select userID, username, userIcon " +
            "from User " +
            "where username = #{username}")
    UserBean getUserProfile(String username);
}
