package com.example.postarrangement.bean;

import lombok.Data;

@Data
public class PlanBean {
    private int planID;
    private String planName;
    private String planInfo;

    @Override
    public String toString() {
        return "PlanBean{" +
                "planID=" + planID +
                ", planName='" + planName + '\'' +
                ", planInfo='" + planInfo + '\'' +
                '}';
    }
}
