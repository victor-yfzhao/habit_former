package org.laorui_out.habit_former.plan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanMessageAdapter {
    private String planType;

    private PlanDetailMessage<DailyPlanBean> dailyPlanDetail;

    private PlanDetailMessage<FitPlanBean> fitPlanDetail;

    private PlanDetailMessage<StudyPlanBean> studyPlanDetail;
}
