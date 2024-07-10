package org.laorui_out.habit_former.poster.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.PosterPictureBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreatePosterService {
    @Resource
    PosterMapper posterMapper;

    public String createPoster(int userID, int planID, String posterHeadline, List<String> posterPicture, String posterDetail){
        PosterBean posterBean = new PosterBean();
        posterBean.setPosterHeadline(posterHeadline);
        posterBean.setPosterDetail(posterDetail);
        posterBean.setUserID(userID);
        posterBean.setPlanID(planID);
        posterBean.setPosterDate(LocalDate.now());
        //获取当前的posterID,和userID一起插入关系表中
        //更新一个poster的信息
        try{
            //
            if(posterPicture == null){
                return "没有图片，加载错误";
            }
            int isUP = posterMapper.insertPoster(posterBean);
            System.out.println(isUP);
            if( isUP < 0){
                return "个人帖子加载失败";
            }
            for(String picture:posterPicture){
                PosterPictureBean posterPictureBean = new PosterPictureBean();
                posterPictureBean.setPosterID(posterBean.getPosterID());
                posterPictureBean.setPosterPicture(picture);
                int isInsertPicture = posterMapper.insertPosterPicture(posterPictureBean);
                if( isInsertPicture < 0){
                    return "图片加载失败";
                }
            }
        }
        catch (Exception e){
            return "出现错误";
        }
        return "上传成功";
    }
}
