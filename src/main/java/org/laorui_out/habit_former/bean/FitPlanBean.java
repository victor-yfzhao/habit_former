package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class FitPlanBean {
    @TableId(value = "fitPlanItemID", type = IdType.AUTO)
    private Integer fitPlanItemID;

    @TableField("date")
    private Date date;

    @TableField("fitItemName")
    private String fitItemName;

    @TableField("fitType")
    private String fitType;

    @TableField("status")
    private String status;

    @TableField("groupNum")
    private Integer groupNum;

    @TableField("numPerGroup")
    private Integer numPerGroup;

    @TableField("timePerGroup")
    private Integer timePerGroup;

    @TableField("planID")
    private Integer planID;

    @Override
    public String toString(){
        return "FitPlanItem{"+
                "fitPlanItemID=" + fitPlanItemID +
                "date='" + date +'\''+
                "fitItemName='" + fitItemName +'\''+
                "fitType='" + fitType +'\''+
                "status='" + status +'\''+
                "groupNum=" + groupNum +
                "numPerGroup=" + numPerGroup +
                "timePerGroup=" + timePerGroup +
                "planID=" + planID +'}';
    }
}
