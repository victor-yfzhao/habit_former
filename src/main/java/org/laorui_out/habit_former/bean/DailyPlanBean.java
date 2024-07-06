package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DailyPlanBean {
    @TableId(type = IdType.AUTO)
    private int dailyPlanID;
    private Date date;
    private String planDetail;
    private String status;
    private String planID;

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
