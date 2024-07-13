package org.laorui_out.habit_former.admin.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
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

    // 计划查询
    @Override
    public IPage<PlanBean> selectAllPlans(Page<PlanBean> page) {
        return planMapper.selectPage(page, null);
    }

    @Override
    public IPage<DailyPlanBean> selectAllDailyPlans(Page<DailyPlanBean> page) {
        return dailyPlanMapper.selectPage(page, null);
    }

    @Override
    public IPage<FitPlanBean> selectAllFitPlans(Page<FitPlanBean> page) {
        return fitPlanMapper.selectPage(page, null);
    }

    @Override
    public IPage<StudyPlanBean> selectAllStudyPlans(Page<StudyPlanBean> page) {
        return studyPlanMapper.selectPage(page, null);
    }

    
    //plan
    @Override
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

    @Override
    public int deletePlanByUserID(int userID){
        List<PlanBean> plans=planMapper.getAllPlanByUserID(userID);
        int cnt=0;
        for (PlanBean item:plans) {
            cnt+=deletePlan(item.getPlanID());
        }
        return cnt;
    }
    
    //dailyPlan
    @Override
    public int deleteDailyPlan(int dailyPlanID){
        return dailyPlanMapper.deleteDailyPlanByID(dailyPlanID);
    }

    @Override
    public int deleteAllDailyPlan(int planID){
        return dailyPlanMapper.deleteAllDailyPlanByPlanID(planID);
    }

    //fitPlan
    @Override
    public int deleteFitPlan(int fitPlanID){
        return fitPlanMapper.deleteFitPlan(fitPlanID);
    }

    @Override
    public int deleteAllFitPlan(int planID){
        return fitPlanMapper.deleteAllFitPlanByPlanID(planID);
    }

    //studyPlan
    @Override
    public int deleteStudyPlan(int studyPlanID){
        return studyPlanMapper.deleteStudyPlan(studyPlanID);
    }

    @Override
    public int deleteAllStudyPlan(int planID){
        return studyPlanMapper.deleteAllStudyPlanByPlanID(planID);
    }
}
