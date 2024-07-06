package org.laorui_out.habit_former.plan.controller;

import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.plan.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class PlanController {
    PlanService planService;
    @Autowired
    PlanController(PlanService planService){

        this.planService=planService;
    }

    //1.GET首页计划列表初始化(需要传id，名称，任务简介)
    //2.GET计划日历初始化(需要传日期，id)
    //3.GET点击计划列表，进入计划详情页面(需要传？？)
    //4.POST修改计划
    //5.POST新建计划(手动+API自动生成后调用)
}
