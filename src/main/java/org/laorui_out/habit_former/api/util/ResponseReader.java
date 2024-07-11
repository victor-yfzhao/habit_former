package org.laorui_out.habit_former.api.util;

import org.json.JSONObject;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.utils.ResponseMessage;


public class ResponseReader {
    public static ResponseMessage<DailyPlanBean> readDPResponse(String jsResponse){
        DailyPlanBean dailyPlanBean=new DailyPlanBean();
        JSONObject jsonObject=new JSONObject(jsResponse);
        dailyPlanBean.setDate(null);
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanDetail(jsonObject.getString("task_name")+':'+jsonObject.getString("task_content"));
        return new ResponseMessage<>(200,"day:"+jsonObject.getInt("day"),dailyPlanBean);
    }
    public static ResponseMessage<FitPlanBean> readFPResponse(String jsResponse){
        FitPlanBean fitPlanBean=new FitPlanBean();
        JSONObject jsonObject=new JSONObject(jsResponse);
        fitPlanBean.setDate(null);
        fitPlanBean.setStatus(Constants.NOT_CHECKED);
        fitPlanBean.setFitItemName(jsonObject.getString("name")+':'+jsonObject.getString("task_content"));
        fitPlanBean.setFitType(jsonObject.getString("num"));
        fitPlanBean.setGroupNum(jsonObject.getInt("task_group_num"));
        fitPlanBean.setTimePerGroup(0);
        fitPlanBean.setNumPerGroup(0);
        if(fitPlanBean.getFitType().equals("秒")||fitPlanBean.getFitType().equals("分钟")||fitPlanBean.getFitType().equals("分"))
            fitPlanBean.setTimePerGroup(jsonObject.getInt("num_per_group"));
        else fitPlanBean.setNumPerGroup(jsonObject.getInt("num_per_group"));
        return new ResponseMessage<>(200,"day:"+jsonObject.getInt("day"),fitPlanBean);
    }
    public static ResponseMessage<StudyPlanBean> readSPResponse(String jsResponse){
        StudyPlanBean studyPlanBean=new StudyPlanBean();
        JSONObject jsonObject=new JSONObject(jsResponse);
        studyPlanBean.setDate(null);
        studyPlanBean.setStatus(Constants.NOT_CHECKED);
        studyPlanBean.setStudyContent(jsonObject.getString("task_name")+':'+jsonObject.getString("task_content"));
        studyPlanBean.setStudyTime(jsonObject.getInt("duration"));
        studyPlanBean.setStudySubject(jsonObject.getString("studySubject"));
        return new ResponseMessage<>(200,"day:"+jsonObject.getInt("day"),studyPlanBean);
    }
}
