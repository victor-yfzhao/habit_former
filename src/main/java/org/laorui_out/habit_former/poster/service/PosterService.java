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
//帖子的一些操作，如增删改查
public class PosterService {
    private final PosterMapper posterMapper;
    private final UserMapper userMapper;

    PosterService(PosterMapper posterMapper,UserMapper userMapper){
        this.posterMapper = posterMapper;
        this.userMapper = userMapper;
    }
//    public boolean deletePosterByPosterId(int posterid){
//        boolean isDelete = posterMapper.deletePosterByPosterId(posterid);
//        return isDelete;
//    }

    //根据posterID获取帖子中的所有图片
    public PosterBean getPosterWithPictures(int posterID) {
        PosterBean posterBean = posterMapper.getPosterById(posterID);
        if (posterBean != null) {
            List<String> pictureUrls = posterMapper.getPosterPicturesByPosterId(posterID);
            posterBean.setPosterPicture(pictureUrls);
        }
        return posterBean;
    }

    //获取所有图片
    public List<PosterBean> getAllPosterWithPictures(){
        List<PosterBean> posterBeanList = posterMapper.getAllPosters();
        for(PosterBean posterBean:posterBeanList){
            posterBean = getPosterWithPictures(posterBean.getPosterID());
        }
        return posterBeanList;
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

    //创建poster
    public String createPoster(int userID,int planID, String posterHeadline,List<String>posterPicture, String posterDetail){
        PosterBean posterBean = new PosterBean();
        posterBean.setPosterHeadline(posterHeadline);
        posterBean.setPosterDetail(posterDetail);
        posterBean.setUserID(userID);
        posterBean.setPlanID(planID);
        posterBean.setPosterDate(LocalDate.now());
        //获取当前的posterID,和userID一起插入关系表中
        //更新一个poster的信息
        try{
            //
            if(posterPicture == null){
                return "没有图片，加载错误";
            }
            int isUP = posterMapper.insertPoster(posterBean);
            System.out.println(isUP);
            if( isUP < 0){
                return "个人帖子加载失败";
            }
            for(String picture:posterPicture){
                PosterPictureBean posterPictureBean = new PosterPictureBean();
                posterPictureBean.setPosterID(posterBean.getPosterID());
                posterPictureBean.setPosterPicture(picture);
                int isInsertPicture = posterMapper.insertPosterPicture(posterPictureBean);
                if( isInsertPicture < 0){
                    return "图片加载失败";
                }
            }
        }
        catch (Exception e){
            return "错误";
        }
        return "上传成功";
    }
}
