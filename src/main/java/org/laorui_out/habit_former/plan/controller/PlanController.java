package org.laorui_out.habit_former.plan.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.plan.service.CheckPlanService;
import org.laorui_out.habit_former.plan.service.PlanDetailService;
import org.laorui_out.habit_former.plan.service.PlanInfoService;
import org.laorui_out.habit_former.plan.utils.Plan4EachDay;
import org.laorui_out.habit_former.plan.utils.PlanDetailMessage;
import org.laorui_out.habit_former.plan.utils.PlanMessageAdapter;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@RestController
public class PlanController {

    @Resource
    PlanInfoService planInfoService;

    @Resource
    PlanDetailService planDetailService;
    @Resource
    CheckPlanService checkPlanService;

    @GetMapping("/index")
    public ResponseMessage<List<PlanBean>> index(@RequestParam int userID) {
        try {
            List<PlanBean> plans = planInfoService.getAllPlanInfo(userID);
            // System.out.println(plans.size());
            return new ResponseMessage<>(200, "success", plans);
        } catch (Exception e) {
            return new ResponseMessage<>(400, e.getMessage(), null);
        }
    }

    @GetMapping("/index/calendar")
    public ResponseMessage<List<Plan4EachDay>> calendarRequest(int userID, int currentYear, int currentMonth) {
        try {
            List<Plan4EachDay> plans = planInfoService.getPlanInMonth(userID, currentYear, currentMonth);
            return new ResponseMessage<>(200, "success", plans);
        } catch (Exception e) {
            return new ResponseMessage<>(400, e.getMessage(), null);
        }
    }

    @GetMapping("/plan_detail")
    public ResponseMessage<PlanMessageAdapter> planDetail(int planID, String date, String planType) {
        switch (planType) {
            case Constants.PLAN_TYPE:
                PlanDetailMessage<DailyPlanBean> plans = planDetailService.getDailyPlanDetail(planID, Date.valueOf(date));
                PlanMessageAdapter message = new PlanMessageAdapter(planType, plans, null, null);
                return new ResponseMessage<>(200, "success", message);
            case Constants.FIT_PLAN_TYPE:
                PlanDetailMessage<FitPlanBean> fitPlans = planDetailService.getFitPlanDetail(planID, Date.valueOf(date));
                message = new PlanMessageAdapter(planType, null, fitPlans, null);
                return new ResponseMessage<>(200, "success", message);
            case Constants.STUDY_PLAN_TYPE:
                PlanDetailMessage<StudyPlanBean> studyPlans = planDetailService.getStudyPlanDetail(planID, Date.valueOf(date));
                message = new PlanMessageAdapter(planType, null, null, studyPlans);
                return new ResponseMessage<>(200, "success", message);
            default:
                return new ResponseMessage<>(400, "UNDEFINED_PLAN_TYPE", null);
        }
    }

    @GetMapping("/calendar_detail")
    public ResponseMessage<PlanMessageAdapter> calendarDetail(int userID, String date) {
        List<PlanBean> plans = planInfoService.getAllPlanInfo(userID);
        PlanDetailMessage<DailyPlanBean> dailyPlans = new PlanDetailMessage<>(), dp_buff;
        PlanDetailMessage<FitPlanBean> fitPlans = new PlanDetailMessage<>(), fp_buff;
        PlanDetailMessage<StudyPlanBean> studyPlans = new PlanDetailMessage<>(), sp_buff;
        for (PlanBean plan : plans) {
            int planID = plan.getPlanID();
            switch (plan.getPlanType()) {
                case Constants.PLAN_TYPE:
                    dp_buff = planDetailService.getDailyPlanDetail(planID, Date.valueOf(date));
                    if (dailyPlans.getPlanItems() == null)
                        dailyPlans.setPlanItems(dp_buff.getPlanItems());
                    else {
                        for (DailyPlanBean item : dp_buff.getPlanItems()) {
                            dailyPlans.getPlanItems().add(item);
                        }
                    }
                    break;
                case Constants.FIT_PLAN_TYPE:
                    fp_buff = planDetailService.getFitPlanDetail(planID, Date.valueOf(date));
                    if (fitPlans.getPlanItems() == null)
                        fitPlans.setPlanItems(fp_buff.getPlanItems());
                    else {
                        for (FitPlanBean item : fp_buff.getPlanItems()) {
                            fitPlans.getPlanItems().add(item);
                        }
                    }
                    break;
                case Constants.STUDY_PLAN_TYPE:
                    sp_buff = planDetailService.getStudyPlanDetail(planID, Date.valueOf(date));
                    if (studyPlans.getPlanItems() == null)
                        studyPlans.setPlanItems(sp_buff.getPlanItems());
                    else {
                        for (StudyPlanBean item : sp_buff.getPlanItems()) {
                            studyPlans.getPlanItems().add(item);
                        }
                    }
                    break;
            }
        }
        PlanMessageAdapter message = new PlanMessageAdapter(null, dailyPlans, fitPlans, studyPlans);
        return new ResponseMessage<>(200, "success", message);
    }

    //TODO:data字段格式的规定？
    @PostMapping("/edit")
    public ResponseMessage<PlanMessageAdapter> editPlanDetail(int planDetailID, String planDetailType, String data) {
        switch (planDetailType) {
            case Constants.PLAN_TYPE:
                break;
            case Constants.FIT_PLAN_TYPE:
                break;
            case Constants.STUDY_PLAN_TYPE:
                break;
            default:
        }
        return null;
    }

    //TODO:定期refresh plan的状态
    @PostMapping("/check")
    public ResponseMessage<String> checkPlanDetail(@RequestParam int planDetailID, int completeStatus, String planDetailType) {

        switch (planDetailType) {
            case Constants.PLAN_TYPE:
                if (completeStatus == 1) {
                    checkPlanService.checkDailyPlan(planDetailID);
                } else if (completeStatus == 0) {
                    checkPlanService.uncheckDailyPlan(planDetailID);
                } else
                    return new ResponseMessage<>(400, "undefined-type", null);
                break;
            case Constants.FIT_PLAN_TYPE:
                if (completeStatus == 1) {
                    checkPlanService.checkFitPlan(planDetailID);
                } else if (completeStatus == 0) {
                    checkPlanService.uncheckFitPlan(planDetailID);
                } else
                    return new ResponseMessage<>(400, "undefined-type", null);
                break;
            case Constants.STUDY_PLAN_TYPE:
                if (completeStatus == 1) {
                    checkPlanService.checkStudyPlan(planDetailID);
                } else if (completeStatus == 0) {
                    checkPlanService.uncheckStudyPlan(planDetailID);
                } else
                    return new ResponseMessage<>(400, "undefined-type", null);
                break;
            default:
        }
        if (completeStatus == 1)
            return new ResponseMessage<>(200, "sucess", "checked");
        else
            return new ResponseMessage<>(200, "sucess", "unchecked");
    }

}
