package com.example.postarrangement.bean;

import lombok.Data;

@Data
public class PDBean {
    private int planID;
    private int dailyPlanID;

    @Override
    public String toString() {
        return "PDBean{" +
                "planID=" + planID +
                ", dailyPlanID=" + dailyPlanID +
                '}';
    }
}
