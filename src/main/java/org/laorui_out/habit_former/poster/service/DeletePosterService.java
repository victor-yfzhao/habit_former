package org.laorui_out.habit_former.poster.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeletePosterService {
    @Resource
    PosterMapper posterMapper;
    @Resource
    PosterService posterService;
    @Resource
    PosterPictureService posterPictureService;

    //根据帖子ID删除帖子
    public Boolean deletePoster(int posterID){

        //对于输入posterID是否存在的判断
        PosterBean posterTestBean = posterPictureService.getPosterWithPictures(posterID);
        if(posterTestBean == null){
            return false;
        }
        try {

            if(posterTestBean.getPosterPicture()==null || posterTestBean.getPosterPicture().isEmpty()){
                return posterMapper.deletePosterByPosterId(posterID);
            }else{
                // 删除 posterpicture 表中的记录
                boolean pictureDeleted = posterMapper.deletePosterPictureByPosterId(posterID);
                // 删除 poster 表中的记录
                boolean posterDeleted = posterMapper.deletePosterByPosterId(posterID);
                return posterDeleted && pictureDeleted;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
