package org.laorui_out.habit_former.poster.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

@Service
public class DeletePosterService {
    @Resource
    PosterMapper posterMapper;

    //根据帖子ID删除帖子
    public Boolean deletePoster(int posterID){
        try {
            // 删除 posterpicture 表中的记录
            boolean pictureDeleted = posterMapper.deletePosterPictureByPosterId(posterID);
            // 删除 poster 表中的记录
            boolean posterDeleted = posterMapper.deletePosterByPosterId(posterID);
            return posterDeleted && pictureDeleted;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
