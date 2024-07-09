package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;

import java.util.Date;
import java.util.List;

@Mapper
public interface DailyPlanMapper extends BaseMapper<DailyPlanBean> {
    //查询
    @Select("select * from dailyplan where planID=#{planID}")
    List<DailyPlanBean> getAllDailyPlanByPlanID(int planID);

    @Select("select * from dailyplan where dailyPlanID=#{dailyPlanID}")
    DailyPlanBean getDailyPlanByID(int dailyPlanID);

    @Select("select * from dailyplan where date=#{date} AND planID=#{planID}")
    List<DailyPlanBean> getDailyPlanByDate(Date date,int planID);

    //插入
    @Insert("insert " +
            "into dailyplan(date,planDetail,status,planID) " +
            "values(#{date},#{planDetail},'"+ Constants.NOT_CHECKED +"',#{planID})")
    @Options(useGeneratedKeys = true, keyProperty = "dailyPlanID")
    int addDailyPlan(DailyPlanBean dailyPlanBean);
    //更新
    @Update("UPDATE dailyplan " +
            "SET planDetail = #{planDetail}, status = #{status} " +
            "WHERE dailyPlanID = #{dailyPlanID}")
    int updateDailyPlan(DailyPlanBean dailyPlanBean);

    //删除
    @Delete("Delete from dailyplan where dailyPlanID=#{dailyPlanID}")
    int deleteDailyPlanByID(int dailyPlanID);

    @Delete("Delete from dailyplan where planID=#{planID}")
    int deleteAllDailyPlanByPlanID(int planID);

}
