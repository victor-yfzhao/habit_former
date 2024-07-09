package org.laorui_out.habit_former.plan.service;

import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.mapper.DailyPlanMapper;
import org.laorui_out.habit_former.mapper.FitPlanMapper;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.mapper.StudyPlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Time;

@Service
public class CreatePlanServiceImpl implements CreatePlanService{
    @Autowired
    PlanMapper planMapper;
    @Autowired
    DailyPlanMapper dailyPlanMapper;
    @Autowired
    FitPlanMapper fitPlanMapper;
    @Autowired
    StudyPlanMapper studyPlanMapper;

    public PlanBean addPlan(String planInfo, String planName,String planType,int userID){
        PlanBean planBean = new PlanBean();
        planBean.setPlanInfo(planInfo);
        planBean.setPlanName(planName);
        planBean.setUserID(userID);
        Date date=new Date();
        Time time=new Time(date.getTime());
        planBean.setPlanDate(date);
        planBean.setPlanTime(time);
        //TODO:planType字段的输入如何限制
        if(planType.equals(Constants.PLAN_TYPE)||planType.equals(Constants.FIT_PLAN_TYPE)||planType.equals(Constants.STUDY_PLAN_TYPE))
            planBean.setPlanType(planType);
        else return null;
        planMapper.addPlan(planBean);
        return planBean;
    }
    public DailyPlanBean addDailyPlan(String planDetail, int planID){
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanID(planID);
        dailyPlanBean.setPlanDetail(planDetail);
        Date date=new Date();
        dailyPlanBean.setDate(date);
        dailyPlanMapper.addDailyPlan(dailyPlanBean);
        return dailyPlanBean;
    }
    public FitPlanBean addFitPlan(String fitItemName,String fitType,int groupNum,int numPerGroup,int timePerGroup, int planID){
        FitPlanBean fitPlanBean = new FitPlanBean();
        fitPlanBean.setFitItemName(fitItemName);
        fitPlanBean.setFitType(fitType);
        fitPlanBean.setGroupNum(groupNum);
        fitPlanBean.setNumPerGroup(numPerGroup);
        fitPlanBean.setTimePerGroup(timePerGroup);
        fitPlanBean.setPlanID(planID);
        fitPlanBean.setStatus(Constants.NOT_CHECKED);
        Date date=new Date();
        fitPlanBean.setDate(date);
        fitPlanMapper.insert(fitPlanBean);
        return fitPlanBean;
    }
    public StudyPlanBean addStudyPlan(String studySubject, String studyContent, int studyTime, int planID){
        StudyPlanBean studyPlanBean = new StudyPlanBean();
        studyPlanBean.setStudyContent(studyContent);
        studyPlanBean.setStudySubject(studySubject);
        studyPlanBean.setStudyTime(studyTime);
        studyPlanBean.setStatus(Constants.NOT_CHECKED);
        Date date = new Date();
        studyPlanBean.setDate(date);
        studyPlanBean.setPlanID(planID);
        studyPlanMapper.insert(studyPlanBean);
        return studyPlanBean;
    }


}
