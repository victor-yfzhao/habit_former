package org.laorui_out.habit_former.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentAndUserBean {
    private Integer commentID;
    private String commentDetail;
    private LocalDate commentDate;
    private LocalTime commentTime;
    private Integer userID;
    private String username;
    private String userIcon;

    @Override
    public String toString() {
        return "CommentAndUserBean{" +
                "commentID=" + commentID +
                ", commentDetail='" + commentDetail + '\'' +
                ", commentDate=" + commentDate +
                ", commentTime=" + commentTime +
                ", userID=" + userID +
                ", username='" + username + '\'' +
                ", userIcon='" + userIcon + '\'' +
                '}';
    }
}
