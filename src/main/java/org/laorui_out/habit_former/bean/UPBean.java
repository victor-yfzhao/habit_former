package org.laorui_out.habit_former.bean;

import lombok.Data;

@Data
public class UPBean {
    private int userID;
    private int posterID;

    @Override
    public String toString() {
        return "UPBean{" +
                "userID=" + userID +
                ", posterID=" + posterID +
                '}';
    }
}
