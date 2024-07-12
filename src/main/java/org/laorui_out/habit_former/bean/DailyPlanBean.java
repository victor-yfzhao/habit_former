package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("DailyPlan")
public class DailyPlanBean {

    @TableId(value = "dailyPlanID", type = IdType.AUTO)
    private Integer dailyPlanID;

    @TableField("date")
    @JsonIgnore
    private Date date;

    @TableField(select = false)
    private String dateShow;

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
