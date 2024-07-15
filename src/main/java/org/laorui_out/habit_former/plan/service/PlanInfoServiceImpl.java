package org.laorui_out.habit_former.plan.service;

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
import org.laorui_out.habit_former.plan.utils.Plan4EachDay;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class PlanInfoServiceImpl extends ServiceImpl<PlanMapper, PlanBean> implements PlanInfoService {

    @Resource
    private DailyPlanMapper dailyPlanMapper;

    @Resource
    private FitPlanMapper fitPlanMapper;

    @Resource
    private StudyPlanMapper studyPlanMapper;
    @Resource
    private PlanMapper planMapper;

    @Override
    public List<PlanBean> getAllPlanInfo(int userID) {
        List<PlanBean> plans = planMapper.getAllPlanByUserID(userID);
        List<PlanBean> res = new ArrayList<>();
        for (PlanBean plan : plans) {
            if (plan.getStatus().equals(Constants.NOT_CHECKED)) {
                plan.setPlanDateShow(Constants.sdf.format(plan.getPlanDate()));
                res.add(plan);
            }
        }
        return res;
    }

    @Override
    public List<PlanBean> getAllHistoryPlanInfo(int userID) {
        List<PlanBean> plans = planMapper.getAllPlanByUserID(userID);
        List<PlanBean> res = new ArrayList<>();
        for (PlanBean plan : plans) {
            if (!plan.getStatus().equals(Constants.NOT_CHECKED)) {
                plan.setPlanDateShow(Constants.sdf.format(plan.getPlanDate()));
                res.add(plan);
            }
        }
        return res;
    }

    @Override
    public List<Plan4EachDay> getPlanInMonth(int userID, int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        Date startDate = new Date(calendar.getTime().getTime());
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        calendar.set(year, month - 1, 1);
        Date endDate = new Date(calendar.getTime().getTime());

        List<PlanBean> planList = getAllPlanInfo(userID);
        if (planList == null) return null;

        List<Plan4EachDay> results = new ArrayList<>();
        for (PlanBean plan : planList) {
            String planType = plan.getPlanType();
            switch (planType) {
                case Constants.FIT_PLAN_TYPE:
                    for (Date date = startDate; date.compareTo(endDate) < 0; date = plusOneDay(date)) {
                        List<FitPlanBean> fitPlan = fitPlanMapper.getFitPlanByDate(plan.getPlanID(), date);
                        addPlan2Result(results, plan, date, fitPlan.isEmpty());
                    }
                    break;
                case Constants.PLAN_TYPE:
                    for (Date date = startDate; date.compareTo(endDate) < 0; date = plusOneDay(date)) {
                        List<DailyPlanBean> dailyPlan = dailyPlanMapper.getDailyPlanByDate(date, plan.getPlanID());
                        addPlan2Result(results, plan, date, dailyPlan.isEmpty());
                    }
                    break;
                case Constants.STUDY_PLAN_TYPE:
                    for (Date date = startDate; date.compareTo(endDate) < 0; date = plusOneDay(date)) {
                        List<StudyPlanBean> studyPlan = studyPlanMapper.getStudyPlanByDate(plan.getPlanID(), date);
                        addPlan2Result(results, plan, date, studyPlan.isEmpty());
                    }
                    break;
                default:
                    break;
            }
        }
        return results;
    }

    private void addPlan2Result(List<Plan4EachDay> results, PlanBean plan, Date date, boolean empty) {
        if (empty) return;

        boolean hit = false;
        for (Plan4EachDay plan4EachDay : results) {
            if (date.compareTo(plan4EachDay.getDate()) == 0) {
                plan4EachDay.getPlanList().add(plan);
                hit = true;
                break;
            }
        }

        if (!hit) {
            List<PlanBean> planBeans = new ArrayList<>();
            planBeans.add(plan);
            results.add(new Plan4EachDay(date, planBeans));
        }
    }

    private Date plusOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTime().getTime());
    }
}
