package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.PlanBean;
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
}
