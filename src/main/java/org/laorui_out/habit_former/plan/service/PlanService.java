package org.laorui_out.habit_former.plan.service;

import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {
    private final PlanMapper planMapper;

    @Autowired
    public PlanService(PlanMapper planMapper) {
        this.planMapper = planMapper;
    }
/*
    public List<PlanBean> getAllPlanBean(int userID) {
        List<PlanBean> res = new ArrayList<>();
        List<Integer> planIDList = new ArrayList<>();
        planIDList = planMapper.getAllPlanIDByUserID(userID);
        for (int id : planIDList) {
            res.add(planMapper.getPlanBeanByID(id));
        }
        return res;
    }

    public List<DailyPlanBean> getAllDailyPlanBean(int planID) {
        List<DailyPlanBean> res = new ArrayList<>();
        List<Integer> dailyPlanIDList = new ArrayList<>();
        dailyPlanIDList = planMapper.getAllDailyPlanIDByPlanID(planID);
        for (int id : dailyPlanIDList) {
            res.add(planMapper.getDailyPlanByID(id));
        }
        return res;
    }
*/
    public List<PlanBean> getUsersAllPlanBean(int userID){
        return planMapper.getAllPlanBeanByUserID(userID);
    }

    public PlanBean getPlanBean(int planID){
        return planMapper.getPlanBeanByPlanID(planID);
    }

    public List<DailyPlanBean> getAllDailyPlan(int planID){
        return planMapper.getAllDailyPlanIDByPlanID(planID);
    }

}