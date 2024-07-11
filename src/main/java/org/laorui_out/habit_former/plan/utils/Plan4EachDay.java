package org.laorui_out.habit_former.plan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.laorui_out.habit_former.bean.PlanBean;

import java.sql.Date;
import java.util.List;

// 供计划首页日历展示使用的返回对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan4EachDay {
    private Date date;                      // 某一天的日期
    private List<PlanBean> planList;        // 当天有的计划
}
