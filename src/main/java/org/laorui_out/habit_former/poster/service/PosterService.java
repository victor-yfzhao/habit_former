package org.laorui_out.habit_former.poster.service;

//import org.laorui_out.habit_former.bean.PosterBean;
//import org.laorui_out.habit_former.bean.PosterPictureBean;
//import java.time.LocalDate;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.LikesBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
//帖子的一些操作，获取信息的操作
public class PosterService {
    @Resource
    PosterMapper posterMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PosterPictureService posterPictureService;

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

    //得到poster的所有帖子ID
    public List<Integer> getAllPosterID(){
        return posterMapper.getAllPosterID();
    }

    public Object getPosterParts(int posterID) {

        PosterBean posterTestBean = posterMapper.getPosterById(posterID);
        if(posterTestBean == null){
            return "帖子ID不存在，缩略信息返回错误";
        }

        //获取用户信息
        UserBean userBean = getUserByPosterId(posterID);
        if(userBean==null){
            return "帖子对应的用户不存在，缩略信息返回错误";
        }

        //获取帖子信息
        PosterBean poster = posterPictureService.getPosterWithPictures(posterID);


        //返回一个匿名类对象
        if(!poster.getPosterPicture().isEmpty()){
            for(int i =0;i<poster.getPosterPicture().size();i++){
                System.out.println(poster.getPosterPicture().get(i));
            }
            return new Object() {
                public final int userID = userBean.getUserID();
                public final String username = userBean.getUsername();
                public final String userIcon = userBean.getUserIcon();
                public final int posterID = poster.getPosterID();
                public final String posterHeadline = poster.getPosterHeadline();
                //主要看这里传参的数据类型
                public final String posterPicture = poster.getPosterPicture().get(0);
            };
        }
        else{
            return "帖子无图片，缩略信息返回错误";
        }
    }

    //判断输入的posterID是否为已经存在的posterID
    public Boolean isInPosterIDList(int posterID){
        List<Integer> posterIDList = posterMapper.getAllPosterID();
        return posterIDList.contains(posterID);
    }

    public List<PosterBean> getPosterByUserID(int userID){
        return posterMapper.getPosterByUserID(userID);
    }

    public String addLikes(LikesBean likesBean){
        int isLike = posterMapper.insertLikes(likesBean);
        if(isLike <= 0){
            return "插入失败";
        }
        else{
            return "插入成功";
        }
    }

    public String addCollection(LikesBean likesBean){
        int isLike = posterMapper.insertCollection(likesBean);
        if(isLike <= 0){
            return "插入失败";
        }
        else{
            return "插入成功";
        }
    }


}
