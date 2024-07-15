package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Comment")
public class CommentBean {
    @TableId(value = "commentID", type = IdType.AUTO)
    private Integer commentID;
    @TableField("commentDetail")
    private String commentDetail;
    @TableField("commentDate")
    private LocalDate commentDate;
    @TableField("commentTime")
    private LocalTime commentTime;
    @TableField("userID")
    private Integer userID;
    @TableField("posterID")
    private Integer posterID;
    @TableField("parentCommentID")
    private Integer parentCommentID; // 使用Integer允许空值

    @TableField(select = false)
    private List<CommentBean> replies;

    @Override
    public String toString() {
        return "CommentBean{" +
                "commentID=" + commentID +
                ", commentDetail='" + commentDetail + '\'' +
                ", commentDate=" + commentDate +
                ", commentTime=" + commentTime +
                ", userID=" + userID +
                ", posterID=" + posterID +
                ", parentCommentID=" + parentCommentID +
                ", replies=" + replies +
                '}';
    }
}
