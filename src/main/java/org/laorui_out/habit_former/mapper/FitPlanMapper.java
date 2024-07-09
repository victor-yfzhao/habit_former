package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.laorui_out.habit_former.bean.FitPlanBean;

import java.sql.Date;
import java.util.List;

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

    // 删除某一健身计划
    @Delete("delete from FitPlanItem " +
            "where fitPlanItemID = #{fitPlanItemID};")
    int deleteFitPlan(int fitPlanID);

    // 更新某一健身计划
    @Update("update FitPlanItem " +
            "set fitType = #{fitSubject}, groupNum = #{groupNum}, " +
            "numPerGroup = #{numPerGroup}, timePerGroup = #{timePerGroup} " +
            "where fitPlanItemID = #{fitPlanItemID};")
    int updateFitPlan(FitPlanBean fitPlanBean);

    // 标记完成状态
    @Update("update FitPlanItem " +
            "set status = #{status} " +
            "where fitPlanItemID = #{fitPlanItemID};")
    int updateFitPlanStatus(FitPlanBean fitPlanBean);

    // 添加健身计划使用默认的insert函数
}
