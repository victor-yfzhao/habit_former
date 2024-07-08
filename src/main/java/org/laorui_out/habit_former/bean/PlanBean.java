package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class PlanBean {
    @TableId(type = IdType.AUTO)
    private Integer planID;
    private String planName;
    private String planInfo;
    private String status;
    private Integer userID;
    private Date planDate;
    private Time planTime;

    @Override
    public String toString() {
        return "PlanBean{" +
                "planID=" + planID +
                ", planName='" + planName + '\'' +
                ", planInfo='" + planInfo + '\'' +
                ", status='" + status + '\'' +
                ", userID=" + userID +
                ", planDate='" + planDate + '\'' +
                ", planTime='" + planTime + '\'' +
                '}';
    }
}
