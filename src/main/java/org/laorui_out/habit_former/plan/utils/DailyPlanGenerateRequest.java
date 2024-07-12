package org.laorui_out.habit_former.plan.utils;

import lombok.Data;
import org.laorui_out.habit_former.bean.DailyPlanBean;

import java.util.List;

@Data
public class DailyPlanGenerateRequest {
    private String type;
    private List<DailyPlanBean> data;
    private int userID;
    private String planName;
    private String planInfo;
}
