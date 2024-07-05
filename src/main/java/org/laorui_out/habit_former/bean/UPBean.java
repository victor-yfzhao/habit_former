package com.example.postarrangement.bean;

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
