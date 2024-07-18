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

import java.util.Date;
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
        switch (planBean.getPlanType()) {
            case Constants.FIT_PLAN_TYPE -> fitPlanMapper.deleteAllFitPlanByPlanID(planID);
            case Constants.STUDY_PLAN_TYPE -> studyPlanMapper.deleteAllStudyPlanByPlanID(planID);
            default -> dailyPlanMapper.deleteAllDailyPlanByPlanID(planID);
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
    @Override
    public PlanBean addPlan(String planName, String planInfo, int userID,String planType){
        PlanBean planBean=new PlanBean();
        Date planDate=new Date(System.currentTimeMillis());
        planBean.setPlanName(planName);
        planBean.setPlanInfo(planInfo);
        planBean.setUserID(userID);
        planBean.setPlanDate(planDate);
        planBean.setPlanType(planType);
        planBean.setStatus(Constants.NOT_CHECKED);
        planMapper.addPlan(planBean);
        return planBean;
    }

    @Override
    public int updatePlan(PlanBean planBean){
        PlanBean item=planMapper.getPlanByPlanID(planBean.getPlanID());
        planBean.setStatus(item.getStatus());
        return planMapper.updatePlan(planBean);
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

    @Override
    public DailyPlanBean addDailyPlan(Date date,String planDetail,int planID){
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setDate(date);
        dailyPlanBean.setPlanDetail(planDetail);
        dailyPlanBean.setPlanID(planID);
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanMapper.addDailyPlan(dailyPlanBean);
        return dailyPlanBean;
    }
    @Override
    public int updateDailyPlan(DailyPlanBean dailyPlanBean){
        return dailyPlanMapper.updateDailyPlan(dailyPlanBean);
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

    @Override
    public FitPlanBean addFitPlan(Date date,String fitItemName,String fitType,int groupNum,int numPerGroup,int timePerGroup,int planID){
        FitPlanBean fitPlanBean=new FitPlanBean();
        fitPlanBean.setDate(date);
        fitPlanBean.setFitItemName(fitItemName);
        fitPlanBean.setFitType(fitType);
        fitPlanBean.setStatus(Constants.NOT_CHECKED);
        fitPlanBean.setGroupNum(groupNum);
        fitPlanBean.setNumPerGroup(numPerGroup);
        fitPlanBean.setTimePerGroup(timePerGroup);
        fitPlanBean.setPlanID(planID);
        fitPlanMapper.insert(fitPlanBean);
        return fitPlanBean;
    }
    @Override
    public int updateFitPlan(FitPlanBean fitPlanBean){
        return fitPlanMapper.updateFitPlan(fitPlanBean);
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

    @Override
    public StudyPlanBean addStudyPlan(Date date,String studySubject,String studyContent,int studyTime,int planID){
        StudyPlanBean studyPlanBean=new StudyPlanBean();
        studyPlanBean.setDate(date);
        studyPlanBean.setStudySubject(studySubject);
        studyPlanBean.setStudyContent(studyContent);
        studyPlanBean.setStatus(Constants.NOT_CHECKED);
        studyPlanBean.setStudyTime(studyTime);
        studyPlanBean.setPlanID(planID);
        studyPlanMapper.insert(studyPlanBean);
        return studyPlanBean;
    }
    @Override
    public int updateStudyPlan(StudyPlanBean studyPlanBean){
        return studyPlanMapper.updateStudyPlan(studyPlanBean);
    }
}
