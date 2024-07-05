package com.example.postarrangement.bean;

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
