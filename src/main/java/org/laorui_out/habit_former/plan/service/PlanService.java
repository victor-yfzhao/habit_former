//package org.laorui_out.habit_former.plan.service;
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import org.laorui_out.habit_former.bean.DailyPlanBean;
//import org.laorui_out.habit_former.bean.PlanBean;
//
//import java.util.List;
//
//public interface PlanService extends IService<PlanBean> {
//    List<PlanBean> getUsersAllPlanBean(int userID);
//    PlanBean getPlanBean(int planID);
//    List<DailyPlanBean> getAllDailyPlan(int planID);
//    DailyPlanBean getDailyPlanBean(int dailyPlanID);
//    DailyPlanBean updateDailyPlanDetail(int dailyPlanID, String detail);
//    boolean deletePlan(int planID);
//    PlanBean addPlan(String planInfo,String planName,int userID);
//    DailyPlanBean addDailyPlan(String planDetail,int planID);
//    boolean checkDailyPlan(int dailyPlanID);
//    void refreshAllPlanStatus(int userID);
//    void refreshPlanStatus(int planID);
//
//}
