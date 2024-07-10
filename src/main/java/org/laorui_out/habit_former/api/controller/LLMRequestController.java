package org.laorui_out.habit_former.api.controller;

import org.laorui_out.habit_former.api.entity.ClientParam;
import org.laorui_out.habit_former.api.entity.PlaningRequest;
import org.laorui_out.habit_former.api.entity.PlaningResponse;
import org.laorui_out.habit_former.api.service.ClientService;
import org.laorui_out.habit_former.api.service.MessageService;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.plan.service.CreatePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planner")
public class LLMRequestController {
    private final MessageService messageService;
    private final ClientService clientService;
    private final CreatePlanService createPlanService;
    @Autowired
    public LLMRequestController(MessageService messageService, ClientService clientService,CreatePlanService createPlanService) {
        this.messageService = messageService;
        this.clientService = clientService;
        this.createPlanService=createPlanService;
    }

    //非流式的请求响应，响应时间过长,不推荐使用
    @PostMapping("/tmp")
    public PlaningResponse handlePlannerRequest(@org.springframework.web.bind.annotation.RequestBody PlaningRequest planingRequest) {

        String messages;
        messages=messageService.getJsonMessage(planingRequest.getData());
        System.out.println(messages);
        ClientParam clientParam=new ClientParam();
        clientService.init(messages,clientParam);
        return clientService.getResponse();
    }

    //流式的请求响应,无prompt，输出格式不标准，不推荐使用
    @PostMapping("/stream")
    public SseEmitter handlePlannerRequestStream(@org.springframework.web.bind.annotation.RequestBody PlaningRequest planingRequest) {
        String messages;
        messages=messageService.getJsonMessageStream(planingRequest.getData());
        System.out.println(messages);
        ClientParam clientParam=new ClientParam();
        clientService.init(messages,clientParam);
        return clientService.getResponseStream();
    }

    //流式的prompt响应（传参的标准待统一）
    @GetMapping("/prompt")
    public SseEmitter handlePlannerRequestStreamPrompt(@RequestParam String theme, String target, String time) {
        //String messages=messageService.getJsonMessageStreamPrompt(theme,target,time);
        //根据不同的theme的值调用不同的messageService方法
        System.out.println("theme:"+theme+"target:"+target+"time:"+time);
        String messages;
        if(theme.equals(Constants.FIT_PLAN_TYPE))
            messages=messageService.getJsonMessageFitPrompt(target,time);
        else if(theme.equals(Constants.STUDY_PLAN_TYPE))
            messages=messageService.getJsonMessageStudyPrompt(target,time);
        else messages=messageService.getJsonMessageStreamPrompt(theme,target,time);

        ClientParam clientParam=new ClientParam();
        clientService.init(messages,clientParam);
        return clientService.getResponseStream();
    }
    @PostMapping("/generate/plan")
    public PlanBean generatePlan(@RequestBody String planInfo, String planName, String planType, int userID){
        PlanBean res = createPlanService.addPlan(planInfo,planName,planType,userID);
        if(res==null){
            //TODO:返回计划类型错误的信息。方法返回值待修改。
            return null;
        }else{
            return res;
        }
    }
    @PostMapping("/generate/dailyplan")//TODO:传参方式待定
    public List<DailyPlanBean> generateDailyPlan(@RequestBody List<String> plans,int planID){
        List<DailyPlanBean> res = new ArrayList<>();
        for (String item:plans) {
            res.add(createPlanService.addDailyPlan(item,planID));
        }
        return res;
    }


}