package org.laorui_out.habit_former.api.controller;

import org.laorui_out.habit_former.api.entity.ClientParam;
import org.laorui_out.habit_former.api.entity.PlaningRequest;
import org.laorui_out.habit_former.api.entity.PlaningResponse;
import org.laorui_out.habit_former.api.service.ClientService;
import org.laorui_out.habit_former.api.service.MessageService;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
@RequestMapping("/api/planner")
public class LLMRequestController {
    private final MessageService messageService;
    private final ClientService clientService;
    @Autowired
    public LLMRequestController(MessageService messageService, ClientService clientService) {
        this.messageService = messageService;
        this.clientService = clientService;
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
        return clientService.getResponseStream("null",new Date());
    }

    //流式的prompt响应（传参的标准待统一）
    @GetMapping("/prompt")
    public SseEmitter handlePlannerRequestStreamPrompt(@RequestParam String theme, String target, String startDate,String endDate) {
        //String messages=messageService.getJsonMessageStreamPrompt(theme,target,time);
        //根据不同的theme的值调用不同的messageService方法
        Date start = java.sql.Date.valueOf(startDate);
        Date end =java.sql.Date.valueOf(endDate);
        int daysBetween = (int)calculateDaysBetween(start,end);
        String time = String.valueOf(daysBetween+1);
        System.out.println("theme:"+theme+"target:"+target+"time:"+time);
        String messages;
        if(theme.equals(Constants.FIT_PLAN_TYPE))
            messages=messageService.getJsonMessageFitPrompt(target,time);
        else if(theme.equals(Constants.STUDY_PLAN_TYPE))
            messages=messageService.getJsonMessageStudyPrompt(target,time);
        else messages=messageService.getJsonMessageStreamPrompt(theme,target,time);

        ClientParam clientParam=new ClientParam();
        clientService.init(messages,clientParam);
        return clientService.getResponseStream(theme,start);
    }

    public static LocalDate convertToLocalDateViaInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // 计算两个日期之间的天数差
    public static long calculateDaysBetween(Date startDate, Date endDate) {
        LocalDate startLocalDate = convertToLocalDateViaInstant(startDate);
        LocalDate endLocalDate = convertToLocalDateViaInstant(endDate);
        return ChronoUnit.DAYS.between(startLocalDate, endLocalDate);
    }


}