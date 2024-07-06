package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PlanBean {
    @TableId(type = IdType.AUTO)
    private int planID;
    private String planName;
    private String planInfo;
    private String status;

    @Override
    public String toString() {
        return "PlanBean{" +
                "planID=" + planID +
                ", planName='" + planName + '\'' +
                ", planInfo='" + planInfo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
