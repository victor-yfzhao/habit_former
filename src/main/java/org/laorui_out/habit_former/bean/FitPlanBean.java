package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("FitPlanItem")
public class FitPlanBean {

    @TableId(value = "fitPlanItemID", type = IdType.AUTO)
    private Integer fitPlanItemID;

    @TableField("date")
    @JsonIgnore
    private Date date;

    @TableField(exist = false)
    private String dateShow;

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
