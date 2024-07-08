package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DailyPlanBean {
    @TableId(type = IdType.AUTO)
    private Integer dailyPlanID;
    @TableField("date")
    private Date date;
    @TableField("planDetail")
    private String planDetail;
    @TableField("status")
    private String status;
    @TableField("planID")
    private Integer planID;

    @Override
    public String toString() {
        return "DailyPlanBean{" +
                "dailyPlanID=" + dailyPlanID +
                ", date=" + date +
                ", planDetail='" + planDetail + '\'' +
                ", status='" + status + '\'' +
                ", planID=" + planID +
                '}';
    }
}
