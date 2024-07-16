package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.springframework.stereotype.Service;

import java.util.Date;

public interface PlanManageService extends IService<PlanBean> {
    int deletePlan(int planID);
    int deletePlanByUserID(int userID);
    PlanBean addPlan(String planName, String planInfo, int userID,String planType);
    int updatePlan(PlanBean planBean);
    int deleteDailyPlan(int dailyPlanID);
    int deleteAllDailyPlan(int planID);
    DailyPlanBean addDailyPlan(Date date, String planDetail, int planID);
    int updateDailyPlan(DailyPlanBean dailyPlanBean);
    int deleteFitPlan(int fitPlanID);
    int deleteAllFitPlan(int planID);
    FitPlanBean addFitPlan(Date date,String fitItemName,String fitType,int groupNum,int numPerGroup,int timePerGroup,int planID);
    int updateFitPlan(FitPlanBean fitPlanBean);
    int deleteStudyPlan(int studyPlanID);
    int deleteAllStudyPlan(int planID);
    StudyPlanBean addStudyPlan(Date date,String studySubject,String studyContent,int studyTime,int planID);
    int updateStudyPlan(StudyPlanBean studyPlanBean);
    IPage<PlanBean> selectAllPlans(Page<PlanBean> page);
    IPage<DailyPlanBean> selectAllDailyPlans(Page<DailyPlanBean> page);
    IPage<FitPlanBean> selectAllFitPlans(Page<FitPlanBean> page);
    IPage<StudyPlanBean> selectAllStudyPlans(Page<StudyPlanBean> page);
}
