package org.laorui_out.habit_former.service;

import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

@Service
//帖子的一些操作，如增删改查
public class PosterService {
    private PosterMapper posterMapper;

    PosterService(PosterMapper posterMapper){
        this.posterMapper = posterMapper;
    }
//    public boolean deletePosterByPosterId(int posterid){
//        boolean isDelete = posterMapper.deletePosterByPosterId(posterid);
//        return isDelete;
//    }
}