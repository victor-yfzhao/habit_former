package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StudyPlanItem {
    @TableId(type = IdType.AUTO)
    private Integer studyPlanItemID;
    private Date date;
    private String studySubject;
    private String studyContent;
    private String status;
    private Integer studyTime;//统一以分钟为单位?
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
