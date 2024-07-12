package org.laorui_out.habit_former.api.service;

import org.laorui_out.habit_former.api.entity.ClientParam;
import org.laorui_out.habit_former.api.entity.PlaningResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Date;

@Service
public interface ClientService{
    void init(String message, ClientParam clientParam);
    PlaningResponse getResponse();
    SseEmitter getResponseStream(String theme, Date startDate);
}