package org.laorui_out.habit_former.service;

import org.apache.catalina.User;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//帖子的一些操作，如增删改查
public class PosterService {
    private PosterMapper posterMapper;
    private UserMapper userMapper;

    PosterService(PosterMapper posterMapper,UserMapper userMapper){
        this.posterMapper = posterMapper;
        this.userMapper = userMapper;
    }
//    public boolean deletePosterByPosterId(int posterid){
//        boolean isDelete = posterMapper.deletePosterByPosterId(posterid);
//        return isDelete;
//    }

    public PosterBean getPosterWithPictures(int posterID) {
        PosterBean posterBean = posterMapper.getPosterById(posterID);
        if (posterBean != null) {
            List<String> pictureUrls = posterMapper.getPosterPicturesByPosterId(posterID);
            posterBean.setPosterPicture(pictureUrls.toArray(new String[0]));
        }
        return posterBean;
    }

    public UserBean getUserByPosterId(int posterID) {
        return userMapper.getUserByPosterId(posterID);
    }

    public List<PosterBean> getAll(){
        return posterMapper.getAll();
    }

}
