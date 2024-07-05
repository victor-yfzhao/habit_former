package com.example.postarrangement.bean;

import lombok.Data;

@Data
public class CommentBean {
    private int commentID;
    private String commentDetail;

    @Override
    public String toString() {
        return "CommentBean{" +
                "commentID=" + commentID +
                ", commentDetail='" + commentDetail + '\'' +
                '}';
    }
}
