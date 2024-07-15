package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;

import java.util.Date;
import java.util.List;

@Mapper
public interface FitPlanMapper extends BaseMapper<FitPlanBean> {

    // 选择特定的fit plan
    @Select("select * " +
            "from FitPlanItem " +
            "where fitPlanItemID = #{fitPlanItemID};")
    FitPlanBean getFitPlanByID(int fitPlanID);

    // 返回某一日所有的健身计划
    @Select("select * " +
            "from FitPlanItem " +
            "where planID = #{planID} and date = #{date};")
    List<FitPlanBean> getFitPlanByDate(int planID, Date date);
    @Select("select * " +
            "from FitPlanItem " +
            "where planID = #{planID} ;")
    List<FitPlanBean> getFitPlanByPlanID(int planID);

    // 删除某一健身计划
    @Delete("delete from FitPlanItem " +
            "where fitPlanItemID = #{fitPlanItemID};")
    int deleteFitPlan(int fitPlanID);
    @Delete("delete from FitPlanItem " +
            "where planID = #{planID};")
    int deleteAllFitPlanByPlanID(int planID);

    // 更新某一健身计划
    @Update("update FitPlanItem " +
            "set fitType = #{fitType}, groupNum = #{groupNum}, " +
            "numPerGroup = #{numPerGroup}, timePerGroup = #{timePerGroup} " +
            "where fitPlanItemID = #{fitPlanItemID};")
    int updateFitPlan(FitPlanBean fitPlanBean);

    // 标记完成状态
    @Update("update FitPlanItem " +
            "set status = #{status} " +
            "where fitPlanItemID = #{fitPlanItemID};")
    int updateFitPlanStatus(FitPlanBean fitPlanBean);

    // 返回某一日完成的健身计划数量
    @Select("select count(*) " +
            "from FitPlanItem " +
            "where status = '"+ Constants.CHECKED +"' and date = #{date};")
    int countDailyFinishedPlan(java.sql.Date date);
    @Select("select * " +
            "from FitPlanItem " +
            "where status = '"+ Constants.CHECKED +"' and date = #{date};")
    List<FitPlanBean> getDailyFinishedPlan(java.sql.Date date);


    // 添加健身计划使用默认的insert函数
}
