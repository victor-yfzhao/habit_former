package org.laorui_out.habit_former.poster.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosterPictureService {
    @Resource
    PosterMapper posterMapper;

    public PosterBean getPosterWithPictures(int posterID) {
        PosterBean posterBean = posterMapper.getPosterById(posterID);
        if (posterBean != null) {
            List<String> pictureUrls = posterMapper.getPosterPicturesByPosterId(posterID);
            if(pictureUrls == null || pictureUrls.isEmpty()){    //如果这个帖子没图片，直接就不传它了！
                return null;
            }
            posterBean.setPosterPicture(pictureUrls);
        }
        return posterBean;
    }

    public List<String> getPosterPicturesByPosterId(int posterID){
        return posterMapper.getPosterPicturesByPosterId(posterID);
    }

    //获取所有带有图片的帖子
    public List<PosterBean> getAllPosterWithPictures(){
        List<PosterBean> posterBeanList = posterMapper.getAllPosters();
        return getPosterBeans(posterBeanList);
    }

    public List<PosterBean> getPosterWithPicturesByTypes(String planType){
        List<PosterBean> posterBeanList = posterMapper.getPostersWithTypes(planType);
        return getPosterBeans(posterBeanList);
    }




    public List<PosterBean> getPosterWithPicturesByUserID(int userID){
        List<PosterBean> posterBeanList = posterMapper.getPosterByUserID(userID);
        return getPosterBeans(posterBeanList);
    }

    private List<PosterBean> getPosterBeans(List<PosterBean> posterBeanList) {
        List<PosterBean> newPosterBeanList = new ArrayList<>();
        if(posterBeanList == null || posterBeanList.isEmpty()){ //此时没有帖子
            return null;
        }
        else{
            for(PosterBean posterBean:posterBeanList){
                posterBean = getPosterWithPictures(posterBean.getPosterID());
                if(posterBean!=null){
                    newPosterBeanList.add(posterBean);
                }
            }
            return newPosterBeanList;
        }
    }

    public List<PosterBean> getPosterCollectionWithPicturesByUserID(int userID){
        List<PosterBean> posterBeanList = posterMapper.getPosterCollectionByUserID(userID);
        return getPosterBeans(posterBeanList);
    }

}
