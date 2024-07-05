package com.example.postarrangement.bean;

import lombok.Data;

import java.awt.*;
@Data
public class UserBean {
    private int userID;
    private String username;
    private String password;
    private String userIcon; //考虑它的类型是什么？,用url去存

    @Override
    public String toString() {
        return "UserBean{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userIcon='" + userIcon + '\'' +
                '}';
    }
}
