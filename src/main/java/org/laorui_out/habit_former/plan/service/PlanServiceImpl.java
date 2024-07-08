package org.laorui_out.habit_former.plan.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, PlanBean> implements PlanService {

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
        return baseMapper.getAllPlanBeanByUserID(userID);
    }

    public PlanBean getPlanBean(int planID){
        return baseMapper.getPlanBeanByPlanID(planID);
    }

    public List<DailyPlanBean> getAllDailyPlan(int planID){
        return baseMapper.getAllDailyPlanIDByPlanID(planID);
    }

}