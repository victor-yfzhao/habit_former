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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CheckPlanServiceImpl implements CheckPlanService {
    @Autowired
    PlanMapper planMapper;
    @Autowired
    DailyPlanMapper dailyPlanMapper;
    @Autowired
    FitPlanMapper fitPlanMapper;
    @Autowired
    StudyPlanMapper studyPlanMapper;

    public boolean checkDailyPlan(int dailyPlanID) {
        DailyPlanBean dailyPlanBean = dailyPlanMapper.getDailyPlanByID(dailyPlanID);
        if (dailyPlanBean.getStatus().equals(Constants.NOT_CHECKED)) {
            dailyPlanBean.setStatus(Constants.CHECKED);
            dailyPlanMapper.updateDailyPlan(dailyPlanBean);
            return true;
        }
        return false;
    }

    //TODO:方案一、uncheck方法在plan处于checked状态时不允许调用
    //TODO:方案二、uncheck方法加一个限时，check方法调用完成后的一定时限内可以uncheck
    public boolean uncheckDailyPlan(int dailyPlanID) {
        DailyPlanBean dailyPlanBean = dailyPlanMapper.getDailyPlanByID(dailyPlanID);
        if (dailyPlanBean.getStatus().equals(Constants.CHECKED)) {
            dailyPlanBean.setStatus(Constants.NOT_CHECKED);
            dailyPlanMapper.updateDailyPlan(dailyPlanBean);
            return true;
        }
        return false;
    }

    public boolean checkFitPlan(int fitPlanID) {
        FitPlanBean fitPlanBean = fitPlanMapper.getFitPlanByID(fitPlanID);
        if (fitPlanBean.getStatus().equals(Constants.NOT_CHECKED)) {
            fitPlanBean.setStatus(Constants.CHECKED);
            fitPlanMapper.updateFitPlanStatus(fitPlanBean);
            return true;
        }
        return false;
    }

    public boolean uncheckFitPlan(int fitPlanID) {
        FitPlanBean fitPlanBean = fitPlanMapper.getFitPlanByID(fitPlanID);
        if (fitPlanBean.getStatus().equals(Constants.CHECKED)) {
            fitPlanBean.setStatus(Constants.NOT_CHECKED);
            fitPlanMapper.updateFitPlanStatus(fitPlanBean);
            return true;
        }
        return false;
    }

    public boolean checkStudyPlan(int studyPlanID) {
        StudyPlanBean studyPlanBean = studyPlanMapper.getStudyPlanByID(studyPlanID);
        if (studyPlanBean.getStatus().equals(Constants.NOT_CHECKED)) {
            studyPlanBean.setStatus(Constants.CHECKED);
            studyPlanMapper.updateStudyPlanStatus(studyPlanBean);
            return true;
        }
        return false;
    }

    public boolean uncheckStudyPlan(int studyPlanID) {
        StudyPlanBean studyPlanBean = studyPlanMapper.getStudyPlanByID(studyPlanID);
        if (studyPlanBean.getStatus().equals(Constants.CHECKED)) {
            studyPlanBean.setStatus(Constants.NOT_CHECKED);
            studyPlanMapper.updateStudyPlanStatus(studyPlanBean);
            return true;
        }
        return false;
    }

    public List<PlanBean> refreshUsersAllPlan(int userID) {
        List<PlanBean> plans = planMapper.getAllPlanByUserID(userID);
        List<PlanBean> result = new ArrayList<>();
        for (PlanBean planbean : plans) {
            PlanBean tmp = refreshPlanStatus(planbean.getPlanID());
            if (tmp != null)//检验是否更新
                result.add(tmp);
            else result.add(planbean);
        }
        return result;
    }

    public PlanBean refreshPlanStatus(int planID) {
        //plan 目前只设定check和uncheck两种状态，不存在fail
        //当所有子计划项都不是uncheck状态的时候，将plan标为check
        PlanBean planBean = planMapper.getPlanByPlanID(planID);
        if (!planBean.getStatus().equals(Constants.NOT_CHECKED))
            return null;
        boolean res;
        //TODO:方法体，调用三种不同的子计划项的检查方法
        if (planBean.getPlanType().equals(Constants.PLAN_TYPE)) {
            res = refreshAllDailyPlan(planID);
        } else if (planBean.getPlanType().equals(Constants.FIT_PLAN_TYPE)) {
            res = refreshAllFitPlan(planID);
        } else if (planBean.getPlanType().equals(Constants.STUDY_PLAN_TYPE)) {
            res = refreshAllStudyPlan(planID);
        } else {
            return null;//TODO:类型异常
        }
        if (res) {
            planBean.setStatus(Constants.CHECKED);
            planMapper.updatePlan(planBean);
        }
        return planBean;
    }

    //返回值代表刷新后该计划是否checked
    public boolean refreshAllDailyPlan(int planID) {
        List<DailyPlanBean> dailyPlanBeanList = dailyPlanMapper.getAllDailyPlanByPlanID(planID);

        for (DailyPlanBean item : dailyPlanBeanList) {
            if (item.getStatus().equals(Constants.NOT_CHECKED)) {
                Date date = item.getDate();
                date = stripTime(date);
                Date now = new Date();
                now = stripTime(now);
                if (now.after(date)) {
                    item.setStatus(Constants.FAILED);
                } else {//有未过期且未打卡的子项目
                    return false;
                }
            }
        }
        //所有子项目均为已打卡或已过期状态
        return true;
    }

    public boolean refreshAllFitPlan(int planID) {
        List<FitPlanBean> fitPlanBeanList = fitPlanMapper.getFitPlanByPlanID(planID);

        for (FitPlanBean item : fitPlanBeanList) {
            if (item.getStatus().equals(Constants.NOT_CHECKED)) {
                Date date = item.getDate();
                date = stripTime(date);
                Date now = new Date();
                now = stripTime(now);
                if (now.after(date)) {
                    item.setStatus(Constants.FAILED);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean refreshAllStudyPlan(int planID) {
        List<StudyPlanBean> studyPlanBeanList = studyPlanMapper.getStudyPlanByPlanID(planID);

        for (StudyPlanBean item : studyPlanBeanList) {
            if (item.getStatus().equals(Constants.NOT_CHECKED)) {
                Date date = item.getDate();
                date = stripTime(date);
                Date now = new Date();
                now = stripTime(now);
                if (now.after(date)) {
                    item.setStatus(Constants.FAILED);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public int countSuccess(int planID) {
        PlanBean planBean = planMapper.getPlanByPlanID(planID);
        int cnt = 0;
        if (planBean.getPlanType().equals(Constants.PLAN_TYPE)) {
            List<DailyPlanBean> dailyPlanBeanList = dailyPlanMapper.getAllDailyPlanByPlanID(planID);
            for (DailyPlanBean item : dailyPlanBeanList) {
                if (item.getStatus().equals(Constants.CHECKED))
                    cnt++;
            }
        } else if (planBean.getPlanType().equals(Constants.FIT_PLAN_TYPE)) {
            List<FitPlanBean> fitPlanBeanList = fitPlanMapper.getFitPlanByPlanID(planID);
            for (FitPlanBean item : fitPlanBeanList) {
                if (item.getStatus().equals(Constants.CHECKED))
                    cnt++;
            }
        } else if (planBean.getPlanType().equals(Constants.STUDY_PLAN_TYPE)) {
            List<StudyPlanBean> studyPlanBeanList = studyPlanMapper.getStudyPlanByPlanID(planID);
            for (StudyPlanBean item : studyPlanBeanList) {
                if (item.getStatus().equals(Constants.CHECKED))
                    cnt++;
            }
        } else {
            return -1;
        }
        return cnt;
    }

    public int countFailed(int planID) {
        PlanBean planBean = planMapper.getPlanByPlanID(planID);
        int cnt = 0;
        if (planBean.getPlanType().equals(Constants.PLAN_TYPE)) {
            List<DailyPlanBean> dailyPlanBeanList = dailyPlanMapper.getAllDailyPlanByPlanID(planID);
            for (DailyPlanBean item : dailyPlanBeanList) {
                if (item.getStatus().equals(Constants.FAILED))
                    cnt++;
            }
        } else if (planBean.getPlanType().equals(Constants.FIT_PLAN_TYPE)) {
            List<FitPlanBean> fitPlanBeanList = fitPlanMapper.getFitPlanByPlanID(planID);
            for (FitPlanBean item : fitPlanBeanList) {
                if (item.getStatus().equals(Constants.FAILED))
                    cnt++;
            }
        } else if (planBean.getPlanType().equals(Constants.STUDY_PLAN_TYPE)) {
            List<StudyPlanBean> studyPlanBeanList = studyPlanMapper.getStudyPlanByPlanID(planID);
            for (StudyPlanBean item : studyPlanBeanList) {
                if (item.getStatus().equals(Constants.FAILED))
                    cnt++;
            }
        } else {
            return -1;
        }
        return cnt;
    }

    public int totalCount(int planID) {
        PlanBean planBean = planMapper.getPlanByPlanID(planID);
        if (planBean.getPlanType().equals(Constants.PLAN_TYPE)) {
            List<DailyPlanBean> dailyPlanBeanList = dailyPlanMapper.getAllDailyPlanByPlanID(planID);
            return dailyPlanBeanList.size();
        } else if (planBean.getPlanType().equals(Constants.FIT_PLAN_TYPE)) {
            List<FitPlanBean> fitPlanBeanList = fitPlanMapper.getFitPlanByPlanID(planID);
            return fitPlanBeanList.size();
        } else if (planBean.getPlanType().equals(Constants.STUDY_PLAN_TYPE)) {
            List<StudyPlanBean> studyPlanBeanList = studyPlanMapper.getStudyPlanByPlanID(planID);
            return studyPlanBeanList.size();
        } else {
            return -1;
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
