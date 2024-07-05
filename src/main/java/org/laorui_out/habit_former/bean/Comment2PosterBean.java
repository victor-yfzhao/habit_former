package com.example.postarrangement.bean;

import lombok.Data;

@Data
public class Comment2PosterBean {
    private int posterID;
    private int commentID;
    private int userID;

    @Override
    public String toString() {
        return "Comment2PosterBean{" +
                "posterID=" + posterID +
                ", commentID=" + commentID +
                ", userID=" + userID +
                '}';
    }
}
