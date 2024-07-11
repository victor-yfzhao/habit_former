package org.laorui_out.habit_former.plan.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.mapper.DailyPlanMapper;
import org.laorui_out.habit_former.mapper.FitPlanMapper;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.mapper.StudyPlanMapper;
import org.laorui_out.habit_former.plan.utils.PlanDetailMessage;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PlanDetailServiceImpl implements PlanDetailService {

    @Resource
    private PlanMapper planMapper;

    @Resource
    private DailyPlanMapper dailyPlanMapper;

    @Resource
    private FitPlanMapper fitPlanMapper;

    @Resource
    private StudyPlanMapper studyPlanMapper;

    @Override
    public PlanDetailMessage<FitPlanBean> getFitPlanDetail(int planID, Date date) {
        PlanBean plan = planMapper.selectById(planID);
        if(!Objects.equals(plan.getPlanType(), Constants.FIT_PLAN_TYPE)) return null;

        PlanDetailMessage<FitPlanBean> message = new PlanDetailMessage<>();
        message.setPlanID(planID);
        message.setDate(date);
        message.setPlanItems(new ArrayList<>());
        message.getPlanItems().addAll(fitPlanMapper.getFitPlanByDate(planID, date));

        return message;
    }

    @Override
    public PlanDetailMessage<StudyPlanBean> getStudyPlanDetail(int planID, Date date) {
        PlanBean plan = planMapper.selectById(planID);
        if(!Objects.equals(plan.getPlanType(), Constants.STUDY_PLAN_TYPE)) return null;

        PlanDetailMessage<StudyPlanBean> message = new PlanDetailMessage<>();
        message.setPlanID(planID);
        message.setDate(date);
        message.setPlanItems(new ArrayList<>());
        message.getPlanItems().addAll(studyPlanMapper.getStudyPlanByDate(planID, date));

        return message;
    }

    @Override
    public PlanDetailMessage<DailyPlanBean> getDailyPlanDetail(int planID, Date date) {
        PlanBean plan = planMapper.selectById(planID);
        if(!Objects.equals(plan.getPlanType(), Constants.PLAN_TYPE)) return null;

        PlanDetailMessage<DailyPlanBean> message = new PlanDetailMessage<>();
        message.setPlanID(planID);
        message.setDate(date);
        message.setPlanItems(new ArrayList<>());
        message.getPlanItems().addAll(dailyPlanMapper.getDailyPlanByDate(date, planID));

        return message;
    }
    @Override
    public DailyPlanBean editDPDetail(DailyPlanBean dailyPlanBean){
        String s = dailyPlanMapper.getDailyPlanByID(dailyPlanBean.getDailyPlanID()).getStatus();
        dailyPlanBean.setStatus(s);
        int res=dailyPlanMapper.updateDailyPlan(dailyPlanBean);
        if(res!=0)
            return dailyPlanBean;
        else return null;//未更新
    }
    @Override
    public FitPlanBean editFPDetail(FitPlanBean fitPlanBean){
        int res=fitPlanMapper.updateFitPlan(fitPlanBean);
        if(res!=0)
            return fitPlanBean;
        else return null;//未更新
    }
    @Override
    public StudyPlanBean editSPDetail(StudyPlanBean studyPlanBean){
        int res=studyPlanMapper.updateStudyPlan(studyPlanBean);
        if(res!=0)
            return studyPlanBean;
        else return null;//未更新
    }
}
