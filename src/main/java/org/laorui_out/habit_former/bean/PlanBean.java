package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Plan")
public class PlanBean {

    @TableId(value = "planID", type = IdType.AUTO)
    private Integer planID;

    @TableField("planName")
    private String planName;

    @TableField("planInfo")
    private String planInfo;

    @TableField("status")
    private String status;

    @TableField("userID")
    private Integer userID;

    @TableField("planDate")
    private Date planDate;

    @TableField("planTime")
    private Time planTime;

    @TableField("planType")
    private String planType;

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
                ", planType='" + planType + '\'' +
                '}';
    }
}

