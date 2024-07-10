package org.laorui_out.habit_former.poster.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterPictureService {
    @Resource
    PosterMapper posterMapper;

    public PosterBean getPosterWithPictures(int posterID) {
        PosterBean posterBean = posterMapper.getPosterById(posterID);
        if (posterBean != null) {
            List<String> pictureUrls = posterMapper.getPosterPicturesByPosterId(posterID);
            posterBean.setPosterPicture(pictureUrls);
        }
        return posterBean;
    }

    //获取所有带有图片的帖子
    public List<PosterBean> getAllPosterWithPictures(){
        List<PosterBean> posterBeanList = posterMapper.getAllPosters();
        for(PosterBean posterBean:posterBeanList){
            posterBean = getPosterWithPictures(posterBean.getPosterID());
        }
        return posterBeanList;
    }
}