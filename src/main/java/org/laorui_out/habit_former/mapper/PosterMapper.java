package org.laorui_out.habit_former.mapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.PosterPictureBean;

import java.util.List;


@Mapper
public interface PosterMapper {


    //根据帖子ID删除poster
//    @Delete("delete from poster where posterID = #{posterID}")
//    public boolean deletePosterByPosterId(@Param("posterID") int posterID);

    //根据帖子ID返回poster
    @Select("select posterID, posterHeadline, posterDetail, userID, planID, posterDate from poster where posterID = #{posterID}")
    PosterBean getPosterById(int posterID);

    //根据帖子ID返回帖子所有图片
    @Select("select posterPicture from posterpicture where posterID = #{posterID}")
    List<String> getPosterPicturesByPosterId(int posterID); // 返回图片URL列表

    //根据帖子ID返回对应计划的标题
    @Select("select planName from plan where planID = (select planID from poster where posterID = #{posterID})")
    String getPlanNameByPosterId(int posterID);

    //根据帖子ID返回点赞数
    @Select("select COUNT(*) from likes where posterID = #{posterID}")
    int getTotalLikes(int posterID);

    //根据帖子ID返回收藏数
    @Select("select COUNT(*) from collection where posterID = #{posterID}")
    int getTotalCollection(int posterID);

    //根据userID选择Poster
//    @Select("select posterID from poster where userID = #{userID}")
//    List<Integer> getPosterIDByUserID(int userID);

    //根据给来的信息创建帖子
    @Insert("INSERT INTO poster (posterHeadline, posterDetail, userID, planID, posterDate) " +
            "VALUES (#{posterHeadline}, #{posterDetail}, #{userID}, #{planID}, #{posterDate})")
    @Options(useGeneratedKeys = true, keyProperty = "posterID", keyColumn = "posterID")
    int insertPoster(PosterBean posterBean);

    //根据给来的信息创建帖子后更新帖子和图片对应的表
    @Insert("INSERT INTO posterpicture (posterID,posterPicture) VALUES (#{posterID},#{posterPicture})")
    int insertPosterPicture(PosterPictureBean posterPictureBean);

//    @Select("select * from poster")
//    List<PosterBean> getAll();

//    @Update()
//
//
//    @Select()


}
