package org.laorui_out.habit_former.plan.service;

import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.plan.utils.PlanDetailMessage;

import java.sql.Date;

public interface PlanDetailService {
    PlanDetailMessage<FitPlanBean> getFitPlanDetail(int planID, Date date);
    PlanDetailMessage<StudyPlanBean> getStudyPlanDetail(int planID, Date date);
    PlanDetailMessage<DailyPlanBean> getDailyPlanDetail(int planID, Date date);
}
