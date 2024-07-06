package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;

import java.util.List;

@Mapper
public interface PlanMapper extends BaseMapper<PlanBean> {
    //查询
    @Select("select planID from userplan where userID=#{userid}")
    List<Integer> getAllPlanIDByUserID(int userid);

    @Select("select * from plan where planID=#{planid}")
    PlanBean getPlanBeanByID(int planid);

    @Select("select dailyPlanID from pd where planID=#{planid}")
    List<Integer> getAllDailyPlanIDByPlanID(int planid);

    @Select("select * from dailyplan where dailyPlanID=#{dailyplanid}")
    DailyPlanBean getDailyPlanByID(int dailyplanid);

    //插入
    @Insert("insert into plan(planName,planInfo,status) values(#{planName},#{planInfo},'"+ Constants.NOT_CHECKED +"')")
    @Options(useGeneratedKeys = true, keyProperty = "planID")
    int addPlanBean(PlanBean planBean);

    @Insert("insert into userplan(planID,userID) Values(#{planID},#{userID})")
    int addPlan2User(int planID,int userID);

    @Insert("insert into dailyplan(date,planDetail,status) values(#{date},#{planDetail},'"+ Constants.NOT_CHECKED +"')")
    @Options(useGeneratedKeys = true, keyProperty = "dailyPlanID")
    int addDailyPlanBean(DailyPlanBean dailyPlanBean);

    @Insert("insert into pd(planID,dailyPlanID) values(#{planID},#{dailyPlanID})")
    int addDailyPlan2Plan(int planID,int dailyPlanID);

    //更新
    @Update("UPDATE plan SET planName = #{planName} WHERE planID = #{planID}")
    int updatePlanName(PlanBean planBean);

    @Update("UPDATE plan SET planInfo = #{planInfo} WHERE planID = #{planID}")
    int updatePlanInfo(PlanBean planBean);

    @Update("UPDATE plan SET status = #{status} WHERE planID = #{planID}")
    int updatePlanStatus(PlanBean planBean);

    @Update("UPDATE dailyplan SET planDetail = #{planDetail} WHERE dailyPlanID = #{dailyPlanID}")
    int updateDailyPlanDetail(DailyPlanBean dailyPlanBean);

    @Update("UPDATE dailyplan SET status = #{status} WHERE dailyPlanID = #{dailyPlanID}")
    int updateDailyPlanStatus(DailyPlanBean dailyPlanBean);

    //删除
    @Delete("Delete from pd where dailyPlanID=#{dailyPlanID}")
    int dropPDByDailyPlanID(int dailyPlanID);

    @Delete("Delete from pd where planID=#{planID}")
    int dropPDByPlanID(int PlanID);

    @Delete("Delete from dailyplan where dailyPlanID=#{dailyPlanID}")
    int dropDailyPlan(int dailyPlanID);


    @Delete("Delete from userplan where planID=#{planID}")
    int dropUPbyPlanID(int PlanID);


    @Delete("Delete from userplan where userID=#{userID}")
    int dropUPbyUserID(int UserID);

    @Delete("Delete from plan where planID=#{planID}")
    int dropPlan(int PlanID);
}