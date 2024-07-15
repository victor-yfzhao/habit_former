package org.laorui_out.habit_former.plan.service;

import org.laorui_out.habit_former.bean.PlanBean;

import java.util.List;

public interface CheckPlanService {
    boolean checkDailyPlan(int dailyPlanID);
    boolean uncheckDailyPlan(int dailyPlanID);
    boolean checkFitPlan(int fitPlanID);
    boolean uncheckFitPlan(int fitPlanID);
    boolean checkStudyPlan(int studyPlanID);
    boolean uncheckStudyPlan(int studyPlanID);
    List<PlanBean> refreshUsersAllPlan(int userID);
    PlanBean refreshPlanStatus(int planID);
    boolean refreshAllDailyPlan(int planID);
    boolean refreshAllFitPlan(int planID);
    boolean refreshAllStudyPlan(int planID);
    int getPlanIDByDP(int dailyPlanID);
    int getPlanIDByFP(int fitPlanID);
    int getPlanIDBySP(int studyPlanID);
    int countSuccess(int planID);
    int countFailed(int planID);
    int totalCount(int planID);
}
