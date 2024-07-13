package org.laorui_out.habit_former.plan.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.plan.service.CheckPlanService;
import org.laorui_out.habit_former.plan.service.CreatePlanService;
import org.laorui_out.habit_former.plan.service.PlanDetailService;
import org.laorui_out.habit_former.plan.service.PlanInfoService;
import org.laorui_out.habit_former.plan.utils.*;
import org.laorui_out.habit_former.utils.ResponseMessage;
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
    @Resource
    CreatePlanService createPlanService;

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
            e.printStackTrace();
            return new ResponseMessage<>(400, e.getMessage(), null);
        }
    }

    @GetMapping("/plan_detail")
    public ResponseMessage<PlanMessageAdapter> planDetail(int planID, String dateShow, String planType) {
        switch (planType) {
            case Constants.PLAN_TYPE:
                PlanDetailMessage<DailyPlanBean> plans = planDetailService.getDailyPlanDetail(planID, Date.valueOf(dateShow));
                PlanMessageAdapter message = new PlanMessageAdapter(planType, plans, null, null);
                return new ResponseMessage<>(200, "success", message);
            case Constants.FIT_PLAN_TYPE:
                PlanDetailMessage<FitPlanBean> fitPlans = planDetailService.getFitPlanDetail(planID, Date.valueOf(dateShow));
                message = new PlanMessageAdapter(planType, null, fitPlans, null);
                return new ResponseMessage<>(200, "success", message);
            case Constants.STUDY_PLAN_TYPE:
                PlanDetailMessage<StudyPlanBean> studyPlans = planDetailService.getStudyPlanDetail(planID, Date.valueOf(dateShow));
                message = new PlanMessageAdapter(planType, null, null, studyPlans);
                return new ResponseMessage<>(200, "success", message);
            default:
                return new ResponseMessage<>(400, "UNDEFINED_PLAN_TYPE", null);
        }
    }

    @GetMapping("/calendar_detail")
    public ResponseMessage<PlanMessageAdapter> calendarDetail(int userID, String dateShow) {
        List<PlanBean> plans = planInfoService.getAllPlanInfo(userID);
        PlanDetailMessage<DailyPlanBean> dailyPlans = new PlanDetailMessage<>(), dp_buff;
        PlanDetailMessage<FitPlanBean> fitPlans = new PlanDetailMessage<>(), fp_buff;
        PlanDetailMessage<StudyPlanBean> studyPlans = new PlanDetailMessage<>(), sp_buff;
        for (PlanBean plan : plans) {
            int planID = plan.getPlanID();
            switch (plan.getPlanType()) {
                case Constants.PLAN_TYPE -> {
                    dp_buff = planDetailService.getDailyPlanDetail(planID, Date.valueOf(dateShow));
                    if (dailyPlans.getPlanItems() == null)
                        dailyPlans.setPlanItems(dp_buff.getPlanItems());
                    else {
                        for (DailyPlanBean item : dp_buff.getPlanItems()) {
                            dailyPlans.getPlanItems().add(item);
                        }
                    }
                }
                case Constants.FIT_PLAN_TYPE -> {
                    fp_buff = planDetailService.getFitPlanDetail(planID, Date.valueOf(dateShow));
                    if (fitPlans.getPlanItems() == null)
                        fitPlans.setPlanItems(fp_buff.getPlanItems());
                    else {
                        for (FitPlanBean item : fp_buff.getPlanItems()) {
                            fitPlans.getPlanItems().add(item);
                        }
                    }
                }
                case Constants.STUDY_PLAN_TYPE -> {
                    sp_buff = planDetailService.getStudyPlanDetail(planID, Date.valueOf(dateShow));
                    if (studyPlans.getPlanItems() == null)
                        studyPlans.setPlanItems(sp_buff.getPlanItems());
                    else {
                        for (StudyPlanBean item : sp_buff.getPlanItems()) {
                            studyPlans.getPlanItems().add(item);
                        }
                    }
                }
            }
        }
        PlanMessageAdapter message = new PlanMessageAdapter(null, dailyPlans, fitPlans, studyPlans);
        return new ResponseMessage<>(200, "success", message);
    }

    //TODO:定期refresh plan的状态
    @PostMapping("/plan_detail/check")
    public ResponseMessage<String> checkPlanDetail(@RequestParam int planDetailID, int completeStatus, String planDetailType) {
    //TODO:要先检查输入的数据是否合法，不然会报500
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
            return new ResponseMessage<>(200, "success", "checked");
        else
            return new ResponseMessage<>(200, "success", "unchecked");
    }
    
    @PostMapping("/new/dailyplan")
    public ResponseMessage<List<DailyPlanBean>> createDailyPlans(@RequestBody DailyPlanGenerateRequest dailyPlanGenerateRequest){
        PlanBean planBean = createPlanService.addPlan(dailyPlanGenerateRequest.getPlanInfo(), dailyPlanGenerateRequest.getPlanName(),dailyPlanGenerateRequest.getType(),dailyPlanGenerateRequest.getUserID());
        List<DailyPlanBean> res=new ArrayList<>();
        for (DailyPlanBean item: dailyPlanGenerateRequest.getData()) {
            DailyPlanBean tmp = createPlanService.addDailyPlan(Date.valueOf(item.getDateShow()), item.getPlanDetail(), planBean.getPlanID());
            tmp.setDateShow(item.getDateShow());
            res.add(tmp);
        }
        return new ResponseMessage<>(200,"success",res);
    }
    @PostMapping("/new/fitplan")
    public ResponseMessage<List<FitPlanBean>> createFitPlans(@RequestBody FitPlanGenerateRequest FitPlanGenerateRequest){
        PlanBean planBean = createPlanService.addPlan(FitPlanGenerateRequest.getPlanInfo(), FitPlanGenerateRequest.getPlanName(),FitPlanGenerateRequest.getType(),FitPlanGenerateRequest.getUserID());
        List<FitPlanBean> res=new ArrayList<>();
        for (FitPlanBean item: FitPlanGenerateRequest.getData()) {
            FitPlanBean tmp = createPlanService.addFitPlan(Date.valueOf(item.getDateShow()), item.getFitItemName(),item.getFitType(),item.getGroupNum(),item.getNumPerGroup(),item.getTimePerGroup(), planBean.getPlanID());
            tmp.setDateShow(item.getDateShow());
            res.add(tmp);
        }
        return new ResponseMessage<>(200,"success",res);
    }
    @PostMapping("/new/studyplan")
    public ResponseMessage<List<StudyPlanBean>> createStudyPlans(@RequestBody StudyPlanGenerateRequest StudyPlanGenerateRequest){
        PlanBean planBean = createPlanService.addPlan(StudyPlanGenerateRequest.getPlanInfo(), StudyPlanGenerateRequest.getPlanName(),StudyPlanGenerateRequest.getType(),StudyPlanGenerateRequest.getUserID());
        List<StudyPlanBean> res=new ArrayList<>();
        for (StudyPlanBean item: StudyPlanGenerateRequest.getData()) {
            StudyPlanBean tmp = createPlanService.addStudyPlan(Date.valueOf(item.getDateShow()), item.getStudySubject(),item.getStudyContent(),item.getStudyTime(), planBean.getPlanID());
            tmp.setDateShow(item.getDateShow());
            res.add(tmp);
        }
        return new ResponseMessage<>(200,"success",res);
    }
    
    @PostMapping("/edit/dailyplan")
    public ResponseMessage<DailyPlanBean> editDailyPlan(@RequestBody DailyPlanBean dailyPlanBean){
        DailyPlanBean res= planDetailService.editDPDetail(dailyPlanBean);
        if(res!=null)
            return new ResponseMessage<>(200,"success",res);
        else return new ResponseMessage<>(400,"failed",null);
    }
    @PostMapping("/edit/fitplan")
    public ResponseMessage<FitPlanBean> editFitPlan(@RequestBody FitPlanBean fitPlanBean){
        FitPlanBean res= planDetailService.editFPDetail(fitPlanBean);
        if(res!=null)
            return new ResponseMessage<>(200,"success",res);
        else return new ResponseMessage<>(400,"failed",null);
    }
    @PostMapping("/edit/studyplan")
    public ResponseMessage<StudyPlanBean> editStudyPlan(@RequestBody StudyPlanBean studyPlanBean){
        StudyPlanBean res= planDetailService.editSPDetail(studyPlanBean);
        if(res!=null)
            return new ResponseMessage<>(200,"success",res);
        else return new ResponseMessage<>(400,"failed",null);
    }

    @PostMapping("/delete")
    public ResponseMessage<String> deletePlan(int planID){
        int res= planDetailService.deletePlan(planID);
        if(res!=0)
            return new ResponseMessage<>(200,"success","deleted.");
        else return new ResponseMessage<>(400,"success","not-found");
    }

}
