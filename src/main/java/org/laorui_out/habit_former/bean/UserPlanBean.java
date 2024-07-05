package org.laorui_out.habit_former.bean;

import lombok.Data;

@Data
public class UserPlanBean {
    private int planID;
    private int userID;

    @Override
    public String toString() {
        return "UserPlanBean{" +
                "planID=" + planID +
                ", userID=" + userID +
                '}';
    }
}
