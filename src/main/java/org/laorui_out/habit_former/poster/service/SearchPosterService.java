package org.laorui_out.habit_former.poster.service;

import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//搜索栏搜索查看帖子
public class SearchPosterService {
    private final PosterMapper posterMapper;
    private final PosterService posterService;

    private final PosterPictureService posterPictureService;

    SearchPosterService(PosterMapper posterMapper, PosterService posterService, PosterPictureService posterPictureService) {
        this.posterMapper = posterMapper;
        this.posterService = posterService;
        this.posterPictureService = posterPictureService;
    }
//    List<PosterBean> getPosterWithWords(String searchWords){
//        return posterMapper.getPosterWithWords(searchWords);
//    }

    public List<PosterBean> getPosterWithWordsAndPictrues(String searchWords){
        String likeSearchWords = '%' + searchWords + '%';
        List<PosterBean> posterBeanList = posterMapper.getPosterWithWords(likeSearchWords);
        List<PosterBean> newPosterBeanList = new ArrayList<PosterBean>();
//        System.out.println("此时的帖子列表");
//        System.out.println(posterBeanList);
        //这里最初没更新图片列表
        for(PosterBean posterBean:posterBeanList){
            posterBean = posterPictureService.getPosterWithPictures(posterBean.getPosterID());
            newPosterBeanList.add(posterBean);
        }
//        System.out.println("更新图片后的帖子列表");
//        System.out.println(newPosterBeanList);
        return newPosterBeanList;
    }


}
