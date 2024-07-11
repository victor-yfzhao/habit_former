package org.laorui_out.habit_former.plan.utils;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class PlanDetailMessage<T> {
    private Integer planID;
    private Date date;
    private List<T> planItems;
}
