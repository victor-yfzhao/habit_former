package org.laorui_out.habit_former.admin.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.DailyPlanMapper;
import org.laorui_out.habit_former.mapper.FitPlanMapper;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.mapper.StudyPlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanManageServiceImpl extends ServiceImpl<PlanMapper, PlanBean> implements PlanManageService {
    @Resource
    private PlanMapper planMapper;

    @Resource
    private DailyPlanMapper dailyPlanMapper;

    @Resource
    private FitPlanMapper fitPlanMapper;

    @Resource
    private StudyPlanMapper studyPlanMapper;
    
    //TODO:每种计划的分页查询
    
    //plan
    public int deletePlan(int planID){
        PlanBean planBean=planMapper.getPlanByPlanID(planID);
        if(planBean==null)
            return -1;
        switch (planBean.getPlanType()){
            case Constants.FIT_PLAN_TYPE:
                fitPlanMapper.deleteAllFitPlanByPlanID(planID);
                break;
            case Constants.STUDY_PLAN_TYPE:
                studyPlanMapper.deleteAllStudyPlanByPlanID(planID);
                break;
            default:
                dailyPlanMapper.deleteAllDailyPlanByPlanID(planID);
                break;
        }
        return planMapper.deletePlanByID(planID);
    }
    public int deletePlanByUserID(int userID){
        List<PlanBean> plans=planMapper.getAllPlanByUserID(userID);
        int cnt=0;
        for (PlanBean item:plans) {
            cnt+=deletePlan(item.getPlanID());
        }
        return cnt;
    }
    
    //dailyPlan
    public int deleteDailyPlan(int dailyPlanID){
        return dailyPlanMapper.deleteDailyPlanByID(dailyPlanID);
    }
    public int deleteAllDailyPlan(int planID){
        return dailyPlanMapper.deleteAllDailyPlanByPlanID(planID);
    }
    //fitPlan
    public int deleteFitPlan(int fitPlanID){
        return fitPlanMapper.deleteFitPlan(fitPlanID);
    }
    public int deleteAllFitPlan(int planID){
        return fitPlanMapper.deleteAllFitPlanByPlanID(planID);
    }
    //studyPlan
    public int deleteStudyPlan(int studyPlanID){
        return studyPlanMapper.deleteStudyPlan(studyPlanID);
    }
    public int deleteAllStudyPlan(int planID){
        return studyPlanMapper.deleteAllStudyPlanByPlanID(planID);
    }
}