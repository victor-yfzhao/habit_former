package org.laorui_out.habit_former.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.plan.utils.Plan4EachDay;

import java.util.List;

public interface PlanInfoService extends IService<PlanBean> {

    // 获取用户所有计划的简要信息
    List<PlanBean> getAllPlanInfo(int userID);
    List<PlanBean> getAllHistoryPlanInfo(int userID);

    // 获取用户某年某月的所有计划，供日历展示
    List<Plan4EachDay> getPlanInMonth(int userID, int year, int month);
}
