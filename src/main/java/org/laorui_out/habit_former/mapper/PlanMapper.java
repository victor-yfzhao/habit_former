package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;

import java.util.List;

@Mapper
public interface PlanMapper extends BaseMapper<PlanBean> {
    //查询v2.0
    @Select("select * from plan where userID=#{userID}")
    List<PlanBean> getAllPlanBeanByUserID(int userID);

    @Select("select * from plan where planID=#{planID}")
    PlanBean getPlanBeanByPlanID(int planID);

    @Select("select * from dailyplan where planID=#{planid}")
    List<DailyPlanBean> getAllDailyPlanIDByPlanID(int planid);

    @Select("select * from dailyplan where dailyPlanID=#{dailyplanid}")
    DailyPlanBean getDailyPlanByID(int dailyplanid);
    //add:查询 （关系模型修正后）



    //插入v2.0
    @Insert("insert " +
            "into plan(planName,planInfo,status,userID,planDate,planTime) " +
            "values(#{planName},#{planInfo},'"+ Constants.NOT_CHECKED +"',#{userID},#{planDate},#{planTime})")
    @Options(useGeneratedKeys = true, keyProperty = "planID")
    int addPlanBean(PlanBean planBean);

    @Insert("insert " +
            "into dailyplan(date,planDetail,status,planID) " +
            "values(#{date},#{planDetail},'"+ Constants.NOT_CHECKED +"',#{planID})")
    @Options(useGeneratedKeys = true, keyProperty = "dailyPlanID")
    int addDailyPlanBean(DailyPlanBean dailyPlanBean);

    //更新v2.0
    //date和time不允许更新
    @Update("UPDATE plan " +
            "SET planName = #{planName}, planInfo = #{planInfo}, status = #{status} " +
            "WHERE planID = #{planID} ")
    int updatePlan(PlanBean planBean);

    @Update("UPDATE dailyplan " +
            "SET planDetail = #{planDetail}, status = #{status} " +
            "WHERE dailyPlanID = #{dailyPlanID}")
    int updateDailyPlan(DailyPlanBean dailyPlanBean);

   //删除v2.0
    @Delete("Delete from dailyplan where dailyPlanID=#{dailyPlanID}")
    int deleteDailyPlanByID(int dailyPlanID);

    @Delete("Delete from dailyplan where planID=#{planID}")
    int deleteAllDailyPlanByPlanID(int planID);

    @Delete("Delete from plan where planID=#{planID}")
    int deletePlanByID(int planID);

    @Delete("Delete from plan where userID=#{userID}")
    int deleteAllPlanByUserID(int userID);


}