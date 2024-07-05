package org.laorui_out.habit_former.bean;

import lombok.Data;

import java.util.Date;

@Data
public class DailyPlanBean {
    private int dailyPlanID;
    private Date date;
    private String planDetail;

    @Override
    public String toString() {
        return "DailyPlanBean{" +
                "dailyPlanID=" + dailyPlanID +
                ", date=" + date +
                ", planDetail='" + planDetail + '\'' +
                '}';
    }
}
