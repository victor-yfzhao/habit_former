package org.laorui_out.habit_former.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.laorui_out.habit_former.admin.utils.CollectsRank;
import org.laorui_out.habit_former.admin.utils.LikesRank;
import org.laorui_out.habit_former.bean.LikesBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.PosterPictureBean;

import java.sql.Date;
import java.util.List;


@Mapper
public interface PosterMapper extends BaseMapper<PosterBean> {


    //根据帖子ID删除poster
    @Delete("delete from Poster where posterID = #{posterID}")
    boolean deletePosterByPosterId(@Param("posterID") int posterID);

    //根据帖子ID删除poster图片
    @Delete("delete from Posterpicture where posterID = #{posterID}")
    boolean deletePosterPictureByPosterId(@Param("posterID") int posterID);

    //直接返回所有poster
//    @Select("select * from Poster order by ")
    @Select("SELECT p.*, " +
            "COALESCE(l.likes, 0) AS numOfLikes, " +
            "COALESCE(c.collections, 0) AS numOfCollections " +
            "FROM Poster p " +
            "LEFT JOIN (SELECT posterID, COUNT(*) AS likes FROM Likes GROUP BY posterID) l " +
            "ON p.posterID = l.posterID " +
            "LEFT JOIN (SELECT posterID, COUNT(*) AS collections FROM Collection GROUP BY posterID) c " +
            "ON p.posterID = c.posterID " +
            "ORDER BY COALESCE(l.likes, 0) + COALESCE(c.collections, 0) DESC")
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

    @Select("select * from Plan where planID = (select planID from Poster where posterID = #{posterID})")
    PlanBean getPlanByPosterId(int posterID);

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
    @Select("select posterID from Poster where userID = #{userID}")
    List<Integer> getAllPosterIDByUserID(int userID);

    @Insert("insert into Collection(userID, posterID) values (#{userID},#{posterID})")
    int insertCollection(LikesBean likesBean);


    @Select("select * from Poster where posterID in (select posterID from Collection where userID = #{userID})")
    List<PosterBean> getPosterCollectionByUserID(int userID);

    //根据信息删除点赞信息
    @Delete("delete from Likes where userID = #{userID} and posterID = #{posterID}")
    boolean deleteLikes(@Param("userID") int userID, @Param("posterID") int posterID);
    @Select("select count(*) from Poster where posterDate = #{posterDate}")
    int countDailyAddedPoster(Date posterDate);

    //根据信息删除收藏信息
    @Delete("delete from Collection where userID = #{userID} and posterID = #{posterID}")
    boolean deleteCollection(@Param("userID") int userID, @Param("posterID") int posterID);

    @Delete("delete from Likes where posterID = #{posterID}")
    boolean deleteLikesByPosterID(@Param("posterID") int posterID);

    @Delete("delete from Collection where posterID = #{posterID}")
    boolean deleteCollectionByPosterID(@Param("posterID") int posterID);

    @Select("select posterID, count(*) as likesCount from Likes GROUP BY posterID ORDER BY likesCount DESC LIMIT #{rankSize}")
    List<LikesRank> getLikesRank(int rankSize);

    @Select("select posterID, count(*) as collectsCount from Collection GROUP BY posterID ORDER BY collectsCount DESC LIMIT #{rankSize}")
    List<CollectsRank> getCollectRank(int rankSize);


    //选择产生对应类型的帖子的缩略信息
    @Select("SELECT Poster.* FROM Poster JOIN Plan ON Poster.planID = Plan.planID WHERE Plan.planType = #{planType}")
    List<PosterBean> getPostersWithTypes(String planType);

//    @Update()
//
//
//    @Select()
//
//

}
