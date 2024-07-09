package org.laorui_out.habit_former.plan.service;

import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.springframework.stereotype.Service;

@Service
public interface CreatePlanService {
    PlanBean addPlan(String planInfo, String planName,String planType,int userID);
    DailyPlanBean addDailyPlan(String planDetail, int planID);
    FitPlanBean addFitPlan(String fitItemName,String fitType,int groupNum,int numPerGroup,int timePerGroup, int planID);
    StudyPlanBean addStudyPlan(String studySubject,String studyContent,int studyTime, int planID);
}
