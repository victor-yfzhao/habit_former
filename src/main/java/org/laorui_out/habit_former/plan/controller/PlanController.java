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
            checkPlanService.refreshUsersAllPlan(userID);
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
        PlanMessageAdapter message;
        switch (planType) {
            case Constants.PLAN_TYPE:
                PlanDetailMessage<DailyPlanBean> plans = planDetailService.getDailyPlanDetail(planID, Date.valueOf(dateShow));
                message = new PlanMessageAdapter(planType, plans, null, null);
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

    @GetMapping("/history")
    public ResponseMessage<List<HistoryPlan>> historyPlan(int userID) {
        try {
            checkPlanService.refreshUsersAllPlan(userID);
            List<PlanBean> plans = planInfoService.getAllHistoryPlanInfo(userID);
            List<HistoryPlan> historyPlans = new ArrayList<>();
            for (PlanBean item : plans) {
                historyPlans.add(new HistoryPlan(item, checkPlanService.countSuccess(item.getPlanID()), checkPlanService.countFailed(item.getPlanID())));
            }
            // System.out.println(plans.size());
            return new ResponseMessage<>(200, "success", historyPlans);
        } catch (Exception e) {
            return new ResponseMessage<>(400, e.getMessage(), null);
        }
    }

    //TODO:定期refresh plan的状态
    @PostMapping("/plan_detail/check")
    public ResponseMessage<String> checkPlanDetail(@RequestParam int planDetailID, int completeStatus, String planDetailType) {
        //TODO:要先检查输入的数据ID是否合法，不然会报500(或者加个try-catch)
        switch (planDetailType) {
            case Constants.FIT_PLAN_TYPE:
                if (completeStatus == 1) {
                    if (!checkPlanService.checkFitPlan(planDetailID))
                        return new ResponseMessage<>(400, "failed, cannot check a finished/expired plan", "check");
                    PlanBean tmp = checkPlanService.refreshPlanStatus(checkPlanService.getPlanIDByFP(planDetailID));
                    if (tmp != null && !(tmp.getStatus().equals(Constants.NOT_CHECKED)))
                        return new ResponseMessage<>(200, "success, whole plan complete!", "checked");
                } else if (completeStatus == 0) {
                    PlanBean tmp = checkPlanService.refreshPlanStatus(checkPlanService.getPlanIDByFP(planDetailID));
                    if (tmp == null || !(tmp.getStatus().equals(Constants.NOT_CHECKED)))
                        return new ResponseMessage<>(400, "failed, cannot uncheck a finished plan", "uncheck");
                    checkPlanService.uncheckFitPlan(planDetailID);
                } else
                    return new ResponseMessage<>(400, "failed", "undefined status");
                break;
            case Constants.STUDY_PLAN_TYPE:
                if (completeStatus == 1) {
                    if (!checkPlanService.checkStudyPlan(planDetailID))
                        return new ResponseMessage<>(400, "failed, cannot check a finished/expired plan", "check");
                    PlanBean tmp = checkPlanService.refreshPlanStatus(checkPlanService.getPlanIDBySP(planDetailID));
                    if (tmp != null && !(tmp.getStatus().equals(Constants.NOT_CHECKED)))
                        return new ResponseMessage<>(200, "success, whole plan complete!", "checked");
                } else if (completeStatus == 0) {
                    PlanBean tmp = checkPlanService.refreshPlanStatus(checkPlanService.getPlanIDBySP(planDetailID));
                    if (tmp == null || !(tmp.getStatus().equals(Constants.NOT_CHECKED)))
                        return new ResponseMessage<>(400, "failed, cannot uncheck a finished plan", "unchecked");
                    checkPlanService.uncheckStudyPlan(planDetailID);
                } else
                    return new ResponseMessage<>(400, "failed", "undefined status");
                break;
            default:
                if (completeStatus == 1) {
                    if (!checkPlanService.checkDailyPlan(planDetailID))
                        return new ResponseMessage<>(400, "failed, cannot check a finished/expired plan", "check");
                    PlanBean tmp = checkPlanService.refreshPlanStatus(checkPlanService.getPlanIDByDP(planDetailID));
                    if (tmp != null && !(tmp.getStatus().equals(Constants.NOT_CHECKED)))
                        return new ResponseMessage<>(200, "success, whole plan complete!", "checked");
                } else if (completeStatus == 0) {
                    PlanBean tmp = checkPlanService.refreshPlanStatus(checkPlanService.getPlanIDByDP(planDetailID));
                    if (tmp == null || !(tmp.getStatus().equals(Constants.NOT_CHECKED)))
                        return new ResponseMessage<>(400, "failed, cannot uncheck a finished plan", "unchecked");
                    checkPlanService.uncheckDailyPlan(planDetailID);
                } else
                    return new ResponseMessage<>(400, "failed", "undefined status");
                break;
        }
        if (completeStatus == 1)
            return new ResponseMessage<>(200, "success", "checked");
        else
            return new ResponseMessage<>(200, "success", "unchecked");
    }

    @PostMapping("/new/dailyplan")
    public ResponseMessage<List<DailyPlanBean>> createDailyPlans(@RequestBody DailyPlanGenerateRequest dailyPlanGenerateRequest) {
        PlanBean planBean = createPlanService.addPlan(dailyPlanGenerateRequest.getPlanInfo(), dailyPlanGenerateRequest.getPlanName(), dailyPlanGenerateRequest.getType(), dailyPlanGenerateRequest.getUserID());
        List<DailyPlanBean> res = new ArrayList<>();
        for (DailyPlanBean item : dailyPlanGenerateRequest.getData()) {
            DailyPlanBean tmp = createPlanService.addDailyPlan(Date.valueOf(item.getDateShow()), item.getPlanDetail(), planBean.getPlanID());
            tmp.setDateShow(item.getDateShow());
            res.add(tmp);
        }
        return new ResponseMessage<>(200, "success", res);
    }

    @PostMapping("/new/fitplan")
    public ResponseMessage<List<FitPlanBean>> createFitPlans(@RequestBody FitPlanGenerateRequest FitPlanGenerateRequest) {
        PlanBean planBean = createPlanService.addPlan(FitPlanGenerateRequest.getPlanInfo(), FitPlanGenerateRequest.getPlanName(), FitPlanGenerateRequest.getType(), FitPlanGenerateRequest.getUserID());
        List<FitPlanBean> res = new ArrayList<>();
        for (FitPlanBean item : FitPlanGenerateRequest.getData()) {
            FitPlanBean tmp = createPlanService.addFitPlan(Date.valueOf(item.getDateShow()), item.getFitItemName(), item.getFitType(), item.getGroupNum(), item.getNumPerGroup(), item.getTimePerGroup(), planBean.getPlanID());
            tmp.setDateShow(item.getDateShow());
            res.add(tmp);
        }
        return new ResponseMessage<>(200, "success", res);
    }

    @PostMapping("/new/studyplan")
    public ResponseMessage<List<StudyPlanBean>> createStudyPlans(@RequestBody StudyPlanGenerateRequest StudyPlanGenerateRequest) {
        PlanBean planBean = createPlanService.addPlan(StudyPlanGenerateRequest.getPlanInfo(), StudyPlanGenerateRequest.getPlanName(), StudyPlanGenerateRequest.getType(), StudyPlanGenerateRequest.getUserID());
        List<StudyPlanBean> res = new ArrayList<>();
        for (StudyPlanBean item : StudyPlanGenerateRequest.getData()) {
            StudyPlanBean tmp = createPlanService.addStudyPlan(Date.valueOf(item.getDateShow()), item.getStudySubject(), item.getStudyContent(), item.getStudyTime(), planBean.getPlanID());
            tmp.setDateShow(item.getDateShow());
            res.add(tmp);
        }
        return new ResponseMessage<>(200, "success", res);
    }


    // TODO 修改计划内容需要根据detailID是否位空来不同处理

    /**
     * 首先，传进来的还是xxxRequest，为如下结构
     * xxxRequest
     * |_planID
     * |_planType
     * |_data
     * |_xxxBean
     * 则，优先判断xxxBean的ID是否为空，为空则参照上面的new，不为空则修改
     */

    @PostMapping("/edit/dailyplan")
    public ResponseMessage<DailyPlanBean> editDailyPlan(@RequestBody DailyPlanBean dailyPlanBean) {
        DailyPlanBean res;
        if (dailyPlanBean.getDailyPlanID() == null)
            res = createPlanService.addDailyPlan(Date.valueOf(dailyPlanBean.getDateShow()), dailyPlanBean.getPlanDetail(), dailyPlanBean.getPlanID());
        else
            res = planDetailService.editDPDetail(dailyPlanBean);
        res.setDateShow(dailyPlanBean.getDateShow());
        if (res != null)
            return new ResponseMessage<>(200, "success", res);
        else return new ResponseMessage<>(400, "failed", null);
    }

    @PostMapping("/edit/fitplan")
    public ResponseMessage<FitPlanBean> editFitPlan(@RequestBody FitPlanBean fitPlanBean) {
        FitPlanBean res;
        if (fitPlanBean.getFitPlanItemID() == null)
            res = createPlanService.addFitPlan(Date.valueOf(fitPlanBean.getDateShow()), fitPlanBean.getFitItemName(), fitPlanBean.getFitType(), fitPlanBean.getGroupNum(), fitPlanBean.getNumPerGroup(), fitPlanBean.getTimePerGroup(), fitPlanBean.getPlanID());
        else
            res = planDetailService.editFPDetail(fitPlanBean);
        res.setDateShow(fitPlanBean.getDateShow());
        if (res != null)
            return new ResponseMessage<>(200, "success", res);
        else return new ResponseMessage<>(400, "failed", null);
    }

    @PostMapping("/edit/studyplan")
    public ResponseMessage<StudyPlanBean> editStudyPlan(@RequestBody StudyPlanBean studyPlanBean) {
        StudyPlanBean res;
        if (studyPlanBean.getStudyPlanItemID() == null)
            res = createPlanService.addStudyPlan(Date.valueOf(studyPlanBean.getDateShow()), studyPlanBean.getStudySubject(), studyPlanBean.getStudyContent(), studyPlanBean.getStudyTime(), studyPlanBean.getPlanID());
        else
            res = planDetailService.editSPDetail(studyPlanBean);
        res.setDateShow(studyPlanBean.getDateShow());
        if (res != null)
            return new ResponseMessage<>(200, "success", res);
        else return new ResponseMessage<>(400, "failed", null);
    }


    @PostMapping("/delete")
    public ResponseMessage<String> deletePlan(int planID) {
        int res = planDetailService.deletePlan(planID);
        if (res != 0)
            return new ResponseMessage<>(200, "success", "deleted.");
        else return new ResponseMessage<>(400, "failed", "not-found");
    }

    @PostMapping("/delete/plan_detail")
    public ResponseMessage<String> deletePlanDetail(int planDetailID, String planType) {
        int res = planDetailService.deletePlanDetail(planDetailID, planType);
        if (res != 0)
            return new ResponseMessage<>(200, "success", "deleted.");
        else return new ResponseMessage<>(400, "failed", "not-found");
    }

}
