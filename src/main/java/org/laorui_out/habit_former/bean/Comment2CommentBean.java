package org.laorui_out.habit_former.bean;

import lombok.Data;

@Data
public class Comment2CommentBean {
    private int UserID;
    private int commentID;
    private int commenteeID;

    @Override
    public String toString() {
        return "Comment2CommentBean{" +
                "UserID=" + UserID +
                ", commentID=" + commentID +
                ", commenteeID=" + commenteeID +
                '}';
    }
}
