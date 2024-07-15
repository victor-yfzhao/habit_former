package org.laorui_out.habit_former.plan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.laorui_out.habit_former.bean.PlanBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPlan {
    private PlanBean planBean;
    private int countSuccess;
    private int countFailed;
}
