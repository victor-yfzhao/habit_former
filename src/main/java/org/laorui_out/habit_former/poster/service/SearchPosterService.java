package org.laorui_out.habit_former.poster.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//搜索栏搜索查看帖子
public class SearchPosterService {
    @Resource
    PosterMapper posterMapper;
    @Resource
    PosterPictureService posterPictureService;

    public List<PosterBean> getPosterWithWordsAndPictrues(String searchWords){
        String likeSearchWords = '%' + searchWords + '%';
        List<PosterBean> posterBeanList = posterMapper.getPosterWithWords(likeSearchWords);
        if(posterBeanList == null || posterBeanList.isEmpty()){
            return null;
        }else{
            List<PosterBean> newPosterBeanList = new ArrayList<>();
            //这里最初没更新图片列表
            for(PosterBean posterBean:posterBeanList){
                posterBean = posterPictureService.getPosterWithPictures(posterBean.getPosterID());
                newPosterBeanList.add(posterBean);
            }
            return newPosterBeanList;
        }
    }

}
