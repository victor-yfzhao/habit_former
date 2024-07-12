package org.laorui_out.habit_former.plan.utils;

import lombok.Data;
import org.laorui_out.habit_former.bean.StudyPlanBean;

import java.util.List;
@Data
public class StudyPlanGenerateRequest {
    private String type;
    private List<StudyPlanBean> data;
    private int userID;
    private String planName;
    private String planInfo;
}
