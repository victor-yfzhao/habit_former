package org.laorui_out.habit_former.plan.service;

import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public List<PlanBean> getUsersAllPlanBean(int userID) {
        return planMapper.getAllPlanBeanByUserID(userID);
    }

    public PlanBean getPlanBean(int planID) {
        return planMapper.getPlanBeanByPlanID(planID);
    }

    public List<DailyPlanBean> getAllDailyPlan(int planID) {
        return planMapper.getAllDailyPlanIDByPlanID(planID);
    }

    public DailyPlanBean getDailyPlanBean(int dailyPlanID) {
        return planMapper.getDailyPlanByID(dailyPlanID);
    }

    public DailyPlanBean updateDailyPlanDetail(int dailyPlanID, String detail) {
        DailyPlanBean dailyPlanBean = planMapper.getDailyPlanByID(dailyPlanID);
        if (dailyPlanBean == null)
            return null;//未找到对应计划任务项 返回null
        dailyPlanBean.setPlanDetail(detail);
        int res = planMapper.updateDailyPlan(dailyPlanBean);
        return dailyPlanBean;
    }

    public boolean deletePlan(int planID) {
        int res = planMapper.deletePlanByID(planID);
        if (res == 0)
            return false;//未找到相应的计划ID
        return true;
    }

    public PlanBean addPlan(String planInfo,String planName,int userID){
        PlanBean planBean = new PlanBean();
        planBean.setPlanInfo(planInfo);
        planBean.setPlanName(planName);
        planBean.setUserID(userID);
        Date date=new Date();
        planBean.setPlanDate(date);
        //TODO:planTime字段冗余问题
        planMapper.addPlanBean(planBean);
        return planBean;
    }
    public DailyPlanBean addDailyPlan(String planDetail,int planID){
        DailyPlanBean dailyPlanBean = new DailyPlanBean();
        dailyPlanBean.setPlanID(planID);
        dailyPlanBean.setPlanDetail(planDetail);
        Date date=new Date();
        dailyPlanBean.setDate(date);
        planMapper.addDailyPlanBean(dailyPlanBean);
        return dailyPlanBean;
    }

    //计划子任务打卡

    public boolean checkDailyPlan(int dailyPlanID) {
        DailyPlanBean dailyPlanBean = planMapper.getDailyPlanByID(dailyPlanID);
        if (dailyPlanBean == null || dailyPlanBean.getStatus().equals(Constants.FAILED))
            return false;//未找到对应计划任务项或者计划项已过期 返回false
        dailyPlanBean.setStatus(Constants.CHECKED);
        int res = planMapper.updateDailyPlan(dailyPlanBean);
        return true;
    }


    //检查刷新指定用户的所有计划的状态并更新
    public void refreshAllPlanStatus(int userID) {
        List<PlanBean> planBeanList = planMapper.getAllPlanBeanByUserID(userID);

        for (PlanBean plan : planBeanList) {
            if (!plan.getStatus().equals(Constants.NOT_CHECKED))
                continue;
            boolean res_checked = true;
            //boolean failed = false;
            List<DailyPlanBean> dailyPlanBeanList = planMapper.getAllDailyPlanIDByPlanID(plan.getPlanID());
            for (DailyPlanBean dailyplan : dailyPlanBeanList) {
                if (dailyplan.getStatus().equals(Constants.CHECKED)) {
                    continue;
                } else if (dailyplan.getStatus().equals(Constants.NOT_CHECKED)) {
                    res_checked = false;
                    Date currentDate = new Date();
                    Date date = dailyplan.getDate();
                    currentDate = stripTime(currentDate);
                    date = stripTime(date);
                    if (currentDate.after(date)) {//已过期
                        dailyplan.setStatus(Constants.FAILED);
                        planMapper.updateDailyPlan(dailyplan);
                    }
                    break;
                } else {
                    res_checked = false;
                    break;
                }
            }
            if (res_checked) {//更新计划状态
                plan.setStatus(Constants.CHECKED);
                planMapper.updatePlan(plan);
            }
        }
    }


    //检查刷新指定计划的状态并更新
    public void refreshPlanStatus(int planID) {
        PlanBean plan = planMapper.getPlanBeanByPlanID(planID);
        if (!plan.getStatus().equals(Constants.NOT_CHECKED))
            return;
        boolean res_checked = true;
        //boolean failed = false;
        List<DailyPlanBean> dailyPlanBeanList = planMapper.getAllDailyPlanIDByPlanID(plan.getPlanID());
        for (DailyPlanBean dailyplan : dailyPlanBeanList) {
            if (dailyplan.getStatus().equals(Constants.CHECKED)) {
                continue;
            } else if (dailyplan.getStatus().equals(Constants.NOT_CHECKED)) {
                res_checked = false;
                Date currentDate = new Date();
                Date date = dailyplan.getDate();
                currentDate = stripTime(currentDate);
                date = stripTime(date);
                if (currentDate.after(date)) {//已过期
                    dailyplan.setStatus(Constants.FAILED);
                    planMapper.updateDailyPlan(dailyplan);
                }
                break;
            } else {
                res_checked = false;
                break;
            }
        }
        if (res_checked) {//更新计划状态
            plan.setStatus(Constants.CHECKED);
            planMapper.updatePlan(plan);
        }

    }


    private static Date stripTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}