package org.laorui_out.habit_former.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage<T> {
    private int code;
    private String message;
    private T data;
}
