package org.laorui_out.habit_former.poster.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.PosterPictureBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreatePosterService {
    @Resource
    PosterMapper posterMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PlanMapper planMapper;


    public String createPoster(int userID, int planID, String posterHeadline, List<String> posterPicture, String posterDetail){
        UserBean userTestBean = userMapper.getUserProfileThroughID(userID);
        if(userTestBean == null){
            return "不存在对应的用户，帖子创建失败";
        }
        PlanBean planTestBean = planMapper.getPlanByPlanID(planID);
        if(planTestBean == null){
            return "不存在对应的计划，帖子创建失败";
        }

        if(posterHeadline==null || posterHeadline.isEmpty()){
            return "帖子标题不能为空";
        }
        if(posterDetail==null || posterDetail.isEmpty()){
            return "帖子详情不能为空";
        }

        //判断计划存不存在！！！！

        PosterBean posterBean = new PosterBean();
        posterBean.setPosterHeadline(posterHeadline);
        posterBean.setPosterDetail(posterDetail);
        posterBean.setUserID(userID);
        posterBean.setPlanID(planID);
        posterBean.setPosterDate(LocalDate.now());
        //获取当前的posterID,和userID一起插入关系表中
        //更新一个poster的信息s
        if(posterPicture == null || posterPicture.isEmpty()){
            return "没有图片，帖子创建失败";
        }
        int isUP = posterMapper.insertPoster(posterBean);
        System.out.println(isUP);
        if( isUP <= 0){
            return "个人帖子创建失败";
        }
        for(String picture:posterPicture){
            PosterPictureBean posterPictureBean = new PosterPictureBean();
            posterPictureBean.setPosterID(posterBean.getPosterID());
            posterPictureBean.setPosterPicture(picture);
            int isInsertPicture = posterMapper.insertPosterPicture(posterPictureBean);
            if( isInsertPicture <= 0){
                return "图片加载失败";
            }
        }

        return "创建成功";
    }
}
