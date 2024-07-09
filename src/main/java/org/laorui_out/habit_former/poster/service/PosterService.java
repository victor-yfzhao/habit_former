package org.laorui_out.habit_former.poster.service;

import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.PosterPictureBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
//帖子的一些操作，获取信息的操作
public class PosterService {
    private final PosterMapper posterMapper;
    private final UserMapper userMapper;

    PosterService(PosterMapper posterMapper,UserMapper userMapper){
        this.posterMapper = posterMapper;
        this.userMapper = userMapper;
    }

    //根据posterID获取对应计划的名字
    public String getPlanNameByPosterId(int posterID) {
        return posterMapper.getPlanNameByPosterId(posterID);
    }

    //根据posterID获取对应的用户的信息
    public UserBean getUserByPosterId(int posterID) {
        return userMapper.getUserByPosterId(posterID);
    }

    //根据posterID返回点赞数
    public int getTotalLikes(int posterID){
        return posterMapper.getTotalLikes(posterID);
    }

    //根据posterID返回收藏数
    public int getTotalCollection(int posterID){
        return posterMapper.getTotalCollection(posterID);
    }

}
