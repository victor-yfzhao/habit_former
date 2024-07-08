package org.laorui_out.habit_former.plan.controller;

import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.plan.service.PlanService;
import org.laorui_out.habit_former.plan.service.PlanServiceImpl;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/index")
public class PlanController {
    PlanService planService;
    @Autowired
    PlanController(PlanServiceImpl planServiceImpl){

        this.planService= planServiceImpl;
    }

    //1.GET首页计划列表初始化(需要传id，名称，任务简介)
    @GetMapping("/init/plan")
    public ResponseMessage<List<PlanBean>> initPlanList(int userID){
        int code=0;String message = null;List<PlanBean> plans = new ArrayList<PlanBean>();
        /*
        方法体
         */
        ResponseMessage<List<PlanBean>> res = new ResponseMessage<>(code,message,plans);
        return res;
    }
    //2.GET计划日历初始化(需要传日期，id)
    @GetMapping("/init/cal")
    public ResponseMessage<List<DailyPlanBean>> initCalendar(int userID){
        int code=0;String message = null;List<DailyPlanBean> plans = new ArrayList<DailyPlanBean>();
        /*
        方法体
         */
        ResponseMessage<List<DailyPlanBean>> res = new ResponseMessage<>(code,message,plans);
        return res;
    }
    //3.GET点击计划列表，进入计划详情页面(单次需要传单个日期的任务项or一次性全部传)
    @GetMapping("/detail/plan")
    public ResponseMessage<List<DailyPlanBean>> getPlanDetail(int planID){
        int code=0;String message = null;List<DailyPlanBean> plans = new ArrayList<DailyPlanBean>();
        /*
        方法体
         */
        ResponseMessage<List<DailyPlanBean>> res = new ResponseMessage<>(code,message,plans);
        return res;
    }
    //4.POST修改子计划项内容dailyplan
    @PostMapping("/edit/plan")
    public ResponseMessage<Boolean> editPlanDetail(String updatedDetail){
        int code=0;String message = null;Boolean flag = true;
        /*
        方法体
         */
        ResponseMessage<Boolean> res = new ResponseMessage<>(code,message,flag);
        return res;
    }
    //*5.POST新建计划(不能手动加+只能API自动生成后调用PlanService)
    //6.POST删除指定的计划PLAN
}
