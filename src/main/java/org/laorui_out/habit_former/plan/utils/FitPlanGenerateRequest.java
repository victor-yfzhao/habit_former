package org.laorui_out.habit_former.plan.utils;

import lombok.Data;
import org.laorui_out.habit_former.bean.FitPlanBean;

import java.util.List;
@Data
public class FitPlanGenerateRequest {
    private String type;
    private List<FitPlanBean> data;
    private int userID;
    private String planName;
    private String planInfo;
}
