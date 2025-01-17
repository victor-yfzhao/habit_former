package org.laorui_out.habit_former.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage<T> {
    private int code;           // http status code (fake)
    private String message;     // service feedback message
    private T data;             // data used for browser
}
