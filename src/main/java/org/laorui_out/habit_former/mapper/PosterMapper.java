package org.laorui_out.habit_former.mapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.bean.LikesBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.PosterPictureBean;

import java.util.List;


@Mapper
public interface PosterMapper {


    //根据帖子ID删除poster
    @Delete("delete from Poster where posterID = #{posterID}")
    boolean deletePosterByPosterId(@Param("posterID") int posterID);

    //根据帖子ID删除poster图片
    @Delete("delete from Posterpicture where posterID = #{posterID}")
    boolean deletePosterPictureByPosterId(@Param("posterID") int posterID);

    //直接返回所有poster
    @Select("select * from Poster")
    List<PosterBean> getAllPosters();

    //根据搜索词返回所有标题或者正文带关键词的poster,${}用于参数绑定
    @Select("SELECT * FROM Poster WHERE posterHeadline LIKE CONCAT('%', #{searchWords}, '%') OR posterDetail LIKE CONCAT('%', #{searchWords}, '%') ")
    List<PosterBean> getPosterWithWords(@Param("searchWords") String searchWords);

    //根据帖子ID返回poster
    @Select("select * from Poster where posterID = #{posterID}")
    PosterBean getPosterById(int posterID);

    //根据帖子ID返回帖子所有图片
    @Select("select posterPicture from Posterpicture where posterID = #{posterID}")
    List<String> getPosterPicturesByPosterId(int posterID); // 返回图片URL列表

    //根据帖子ID返回对应计划的标题
    @Select("select planName from Plan where planID = (select planID from Poster where posterID = #{posterID})")
    String getPlanNameByPosterId(int posterID);

    @Select("select planType from Plan where planID = (select planID from Poster where posterID = #{posterID})")
    String getPlanTypeByPosterId(int posterID);

    //根据帖子ID返回点赞数
    @Select("select COUNT(*) from Likes where posterID = #{posterID}")
    int getTotalLikes(int posterID);

    //根据帖子ID返回收藏数
    @Select("select COUNT(*) from Collection where posterID = #{posterID}")
    int getTotalCollection(int posterID);

    //根据给来的信息创建帖子
    @Insert("INSERT INTO Poster (posterHeadline, posterDetail, userID, planID, posterDate) " +
            "VALUES (#{posterHeadline}, #{posterDetail}, #{userID}, #{planID}, #{posterDate})")
    @Options(useGeneratedKeys = true, keyProperty = "posterID", keyColumn = "posterID")
    int insertPoster(PosterBean posterBean);

    //根据给来的信息创建帖子后更新帖子和图片对应的表
    @Insert("INSERT INTO Posterpicture (posterID,posterPicture) VALUES (#{posterID},#{posterPicture})")
    int insertPosterPicture(PosterPictureBean posterPictureBean);

    @Select("select posterID from Poster")
    List<Integer> getAllPosterID();

    @Select("select * from Poster where userID = #{userID}")
    List<PosterBean> getPosterByUserID(int userID);

    @Insert("insert into Likes(userID, posterID) values (#{userID}, #{posterID})")
    int insertLikes(LikesBean likesBean);

    @Insert("insert into Collection(userID, posterID) values (#{userID},#{posterID})")
    int insertCollection(LikesBean likesBean);


    @Select("select * from Poster where posterID in (select posterID from Collection where userID = #{userID})")
    List<PosterBean> getPosterCollectionByUserID(int userID);

    //根据信息删除点赞信息
    @Delete("delete from Likes where userID = #{userID} and posterID = #{posterID}")
    boolean deleteLikes(@Param("userID") int userID, @Param("posterID") int posterID);

    //根据信息删除收藏信息
    @Delete("delete from Collection where userID = #{userID} and posterID = #{posterID}")
    boolean deleteCollection(@Param("userID") int userID, @Param("posterID") int posterID);

    @Delete("delete from Likes where posterID = #{posterID}")
    boolean deleteLikesByPosterID(@Param("posterID") int posterID);

    @Delete("delete from Collection where posterID = #{posterID}")
    boolean deleteCollectionByPosterID(@Param("posterID") int posterID);
//    @Update()
//
//
//    @Select()
//
//

}
