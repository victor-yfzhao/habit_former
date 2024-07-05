package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.laorui_out.habit_former.bean.UserBean;

@Mapper
public interface UserMapper extends BaseMapper<UserBean> {
//    @Select("select * from user where username = #{username}")
//    UserBean getUserByUsername(String username);

    @Select("SELECT userID, username, userIcon FROM User WHERE " +
            "userID = (SELECT userID FROM posting WHERE posterID = #{posterID})")
    UserBean getUserByPosterId(@Param("posterID") int posterID);
}
