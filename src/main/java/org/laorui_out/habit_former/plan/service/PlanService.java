package org.laorui_out.habit_former.plan.service;

import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService {
    private final PlanMapper planMapper;
    @Autowired
    public PlanService(PlanMapper planMapper) {
        this.planMapper = planMapper;
    }
//    public PlanBean getPlanByID(String ID){
//
//        return planMapper.getPlanBeanByUserID(ID);
//    }
}