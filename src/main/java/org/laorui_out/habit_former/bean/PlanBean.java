package org.laorui_out.habit_former.bean;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PlanBean {
    private int planID;
    private String planName;
    private String planInfo;
    private int userID;//外键
    private LocalDate planDate;
    private LocalTime planTime;
    private String status;
    private String planType;

    @Override
    public String toString() {
        return "PlanBean{" +
                "planID=" + planID +
                ", planName='" + planName + '\'' +
                ", planInfo='" + planInfo + '\'' +
                ", userID=" + userID +
                ", planDate=" + planDate +
                ", planTime=" + planTime +
                ", status='" + status + '\'' +
                ", planType='" + planType + '\'' +
                '}';
    }
}
