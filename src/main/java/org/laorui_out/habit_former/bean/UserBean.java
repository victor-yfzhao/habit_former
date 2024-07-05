package org.laorui_out.habit_former.bean;

import lombok.Data;

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
