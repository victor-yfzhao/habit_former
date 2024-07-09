package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("StudyPlanItem")
public class StudyPlanBean {

    @TableId(value = "studyPlanItemID", type = IdType.AUTO)
    private Integer studyPlanItemID;

    @TableField("date")
    private Date date;

    @TableField("studySubject")
    private String studySubject;

    @TableField("studyContent")
    private String studyContent;

    @TableField("status")
    private String status;

    @TableField("studyTime")
    private Integer studyTime;//统一以分钟为单位?

    @TableField("planID")
    private Integer planID;

    @Override
    public String toString(){
        return "StudyPlanItem{"+
                "studyPlanItemID=" + studyPlanItemID +
                "date='" + date +'\''+
                "studySubject='" + studySubject +'\''+
                "studyContent='" + studyContent +'\''+
                "status='" + status +'\''+
                "studyTime=" + studyTime +
                "planID=" + planID +'}';
    }
}
