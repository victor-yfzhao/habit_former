package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;

import java.sql.Date;
import java.util.List;
@Mapper
public interface StudyPlanMapper extends BaseMapper<StudyPlanBean> {

    // 选择特定的study plan
    @Select("select * " +
            "from StudyPlanItem " +
            "where studyPlanItemID = #{studyPlanItemID};")
    StudyPlanBean getStudyPlanByID(int studyPlanID);

    // 返回某一日所有的学习计划
    @Select("select * " +
            "from StudyPlanItem " +
            "where planID = #{planID} and date = #{date};")
    List<StudyPlanBean> getStudyPlanByDate(int planID, Date date);
    @Select("select * " +
            "from StudyPlanItem " +
            "where planID = #{planID} ;")
    List<StudyPlanBean> getStudyPlanByPlanID(int planID);

    // 删除某一学习计划
    @Delete("delete from StudyPlanItem " +
            "where studyPlanItemID = #{studyPlanItemID};")
    int deleteStudyPlan(int studyPlanID);
    @Delete("delete from StudyPlanItem " +
            "where planID = #{planID};")
    int deleteAllStudyPlanByPlanID(int planID);

    // 更新某一学习计划
    @Update("update StudyPlanItem " +
            "set studySubject = #{studySubject}, studyContent = #{studyContent}, studyTime = #{studyTime} " +
            "where studyPlanItemID = #{studyPlanItemID};")
    int updateStudyPlan(StudyPlanBean studyPlanBean);

    // 标记完成状态
    @Update("update StudyPlanItem " +
            "set status = #{status} " +
            "where studyPlanItemID = #{studyPlanItemID};")
    int updateStudyPlanStatus(StudyPlanBean studyPlanBean);

    // 返回某一日完成的学习计划数量
    @Select("select count(*) " +
            "from StudyPlanItem " +
            "where status = '"+ Constants.CHECKED +"' and date = #{date};")
    int countDailyFinishedPlan(Date date);

    @Select("select * " +
            "from StudyPlanItem " +
            "where status = '"+ Constants.CHECKED +"' and date = #{date};")
    List<StudyPlanBean> getDailyFinishedPlan(Date date);

    // 添加学习计划使用默认的insert函数
}
