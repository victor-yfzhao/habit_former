package org.laorui_out.habit_former.api.controller;

import org.laorui_out.habit_former.api.entity.ClientParam;
import org.laorui_out.habit_former.api.entity.PlaningRequest;
import org.laorui_out.habit_former.api.entity.PlaningResponse;
import org.laorui_out.habit_former.api.service.ClientService;
import org.laorui_out.habit_former.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

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

    //非流式的请求响应，响应时间过长
    @PostMapping("/tmp")
    public PlaningResponse handlePlannerRequest(@org.springframework.web.bind.annotation.RequestBody PlaningRequest planingRequest) throws IOException {

        String messages;
        messages=messageService.getJsonMessage(planingRequest.getData());
        System.out.println(messages);
        ClientParam clientParam=new ClientParam();
        clientService.init(messages,clientParam);
        return clientService.getResponse();
    }

    //流式的请求响应
    @PostMapping("/stream")
    public SseEmitter handlePlannerRequestStream(@org.springframework.web.bind.annotation.RequestBody PlaningRequest planingRequest) throws IOException {
        String messages;
        messages=messageService.getJsonMessageStream(planingRequest.getData());
        System.out.println(messages);
        ClientParam clientParam=new ClientParam();
        clientService.init(messages,clientParam);
        return clientService.getResponseStream();
    }

    //流式的prompt响应（传参的标准待修改）
    @PostMapping("/prompt")
    public SseEmitter handlePlannerRequestStreamPrompt(@org.springframework.web.bind.annotation.RequestBody String theme,String target,String time) throws IOException {
        String messages=messageService.getJsonMessageStreamPrompt(theme,target,time);
        ClientParam clientParam=new ClientParam();
        clientService.init(messages,clientParam);
        return clientService.getResponseStream();
    }
}