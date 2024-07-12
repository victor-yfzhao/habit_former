package org.laorui_out.habit_former.api.util;

import org.json.JSONObject;
import org.laorui_out.habit_former.bean.DailyPlanBean;
import org.laorui_out.habit_former.bean.FitPlanBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;
import org.laorui_out.habit_former.plan.constant.Constants;
import org.laorui_out.habit_former.utils.ResponseMessage;

import java.util.Calendar;
import java.util.Date;


public class ResponseReader {
    public static ResponseMessage<DailyPlanBean> readDPResponse(String jsResponse,Date startDate){
        DailyPlanBean dailyPlanBean=new DailyPlanBean();
        JSONObject jsonObject=new JSONObject(jsResponse);
        int days=jsonObject.getInt("day");
        dailyPlanBean.setDate(calculateFutureDate(startDate,days-1));
        dailyPlanBean.setDateShow(Constants.sdf.format(dailyPlanBean.getDate()));
        dailyPlanBean.setStatus(Constants.NOT_CHECKED);
        dailyPlanBean.setPlanDetail(jsonObject.getString("task_name")+':'+jsonObject.getString("task_content"));
        return new ResponseMessage<>(200,"day:"+jsonObject.getInt("day"),dailyPlanBean);
    }
    public static ResponseMessage<FitPlanBean> readFPResponse(String jsResponse,Date startDate){
        FitPlanBean fitPlanBean=new FitPlanBean();
        JSONObject jsonObject=new JSONObject(jsResponse);
        int days=jsonObject.getInt("day");
        fitPlanBean.setDate(calculateFutureDate(startDate,days-1));
        fitPlanBean.setDateShow(Constants.sdf.format(fitPlanBean.getDate()));
        fitPlanBean.setStatus(Constants.NOT_CHECKED);
        fitPlanBean.setFitItemName(jsonObject.getString("task_content")+':'+jsonObject.getString("name"));
        fitPlanBean.setFitType(jsonObject.getString("num"));
        fitPlanBean.setGroupNum(jsonObject.getInt("task_group_num"));
        fitPlanBean.setTimePerGroup(0);
        fitPlanBean.setNumPerGroup(0);
        if(fitPlanBean.getFitType().equals("秒")||fitPlanBean.getFitType().equals("分钟")||fitPlanBean.getFitType().equals("分"))
            fitPlanBean.setTimePerGroup(jsonObject.getInt("num_per_group"));
        else fitPlanBean.setNumPerGroup(jsonObject.getInt("num_per_group"));
        return new ResponseMessage<>(200,"day:"+jsonObject.getInt("day"),fitPlanBean);
    }
    public static ResponseMessage<StudyPlanBean> readSPResponse(String jsResponse,Date startDate){
        StudyPlanBean studyPlanBean=new StudyPlanBean();
        JSONObject jsonObject=new JSONObject(jsResponse);
        int days=jsonObject.getInt("day");
        studyPlanBean.setDate(calculateFutureDate(startDate,days-1));
        studyPlanBean.setDateShow(Constants.sdf.format(studyPlanBean.getDate()));
        studyPlanBean.setStatus(Constants.NOT_CHECKED);
        studyPlanBean.setStudyContent(jsonObject.getString("task_name")+':'+jsonObject.getString("task_content"));
        studyPlanBean.setStudyTime(jsonObject.getInt("duration"));
        studyPlanBean.setStudySubject(jsonObject.getString("studySubject"));
        return new ResponseMessage<>(200,"day:"+jsonObject.getInt("day"),studyPlanBean);
    }
    private static Date calculateFutureDate(Date startDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}
