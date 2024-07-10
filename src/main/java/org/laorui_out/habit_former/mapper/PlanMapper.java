package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;
import java.util.Date;
import java.util.List;

@Mapper
public interface PlanMapper extends BaseMapper<PlanBean> {
    //查询v2.0
    @Select("select * from plan where userID=#{userID}")
    List<PlanBean> getAllPlanByUserID(int userID);

    @Select("select * from plan where planID=#{planID}")
    PlanBean getPlanByPlanID(int planID);

    @Select("select * from plan where planDate=#{date} AND userID=#{userID}")
    List<PlanBean> getPlanByDate(Date date, int userID);

    //插入v2.0
    @Insert("insert " +
            "into plan(planName,planInfo,status,userID,planDate,planTime,planType) " +
            "values(#{planName},#{planInfo},'"+ Constants.NOT_CHECKED +"',#{userID},#{planDate},#{planTime},#{planType})")
    @Options(useGeneratedKeys = true, keyProperty = "planID")
    int addPlan(PlanBean planBean);

    //更新v2.0
    //date和time不允许更新
    @Update("UPDATE plan " +
            "SET planName = #{planName}, planInfo = #{planInfo}, status = #{status} " +
            "WHERE planID = #{planID} ")
    int updatePlan(PlanBean planBean);

    //删除v2.0
    @Delete("Delete from plan where planID=#{planID}")
    int deletePlanByID(int planID);

    @Delete("Delete from plan where userID=#{userID}")
    int deleteAllPlanByUserID(int userID);


}