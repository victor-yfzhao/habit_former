package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.laorui_out.habit_former.bean.LoginBean;

public interface LoginMapper extends BaseMapper<LoginBean> {
    @Insert("insert into sys_log(userID,Log_Content,IP_Address,OS,IE,CreateDate,Remark) " +
            "values(#{userID},#{Log_Content},#{IP_Address},#{OS},#{IE},#{CreateDate},#{Remark})")
    int insertLogin(LoginBean loginBean);
}
