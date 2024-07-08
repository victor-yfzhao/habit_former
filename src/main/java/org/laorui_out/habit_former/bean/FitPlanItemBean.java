package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class FitPlanItemBean {
    @TableId(type = IdType.AUTO)
    private Integer fitPlanItemID;
    private Date date;
    private String fitItemName;
    private String fitType;
    private String status;
    private Integer groupNum;
    private Integer numPerGroup;
    private Integer timePerGroup;
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
