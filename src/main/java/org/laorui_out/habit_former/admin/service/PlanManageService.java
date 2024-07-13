package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.springframework.stereotype.Service;

public interface PlanManageService extends IService<PlanBean> {
    int deletePlan(int planID);
    int deletePlanByUserID(int userID);
    int deleteDailyPlan(int dailyPlanID);
    int deleteAllDailyPlan(int planID);
    int deleteFitPlan(int fitPlanID);
    int deleteAllFitPlan(int planID);
    int deleteStudyPlan(int studyPlanID);
    int deleteAllStudyPlan(int planID);

    IPage<PlanBean> selectAllPlans(Page<PlanBean> page);
    IPage<DailyPlanBean> selectAllDailyPlans(Page<DailyPlanBean> page);
    IPage<FitPlanBean> selectAllFitPlans(Page<FitPlanBean> page);
    IPage<StudyPlanBean> selectAllStudyPlans(Page<StudyPlanBean> page);
}
