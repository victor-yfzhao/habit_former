package org.laorui_out.habit_former.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.laorui_out.habit_former.bean.CommentBean;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<CommentBean> {
    @Select("select * from Comment where commentID = #{commentID}")
    CommentBean getCommentByCommentID(int commentID);
    @Select("select * from Comment where posterID = #{posterID} and parentCommentID = -1")
    List<CommentBean> getCommentByPosterID(int posterID);

    @Select("select * from Comment where parentCommentID = #{commentID}")
    List<CommentBean> getChildComment(int commentID);

    @Insert("insert into Comment(userID, posterID, parentCommentID, commentDetail, commentDate, commentTime)" +
            "values (#{userID}, #{posterID}, #{parentCommentID}, #{commentDetail}, #{commentDate}, #{commentTime})")
    @Options(useGeneratedKeys = true, keyProperty = "commentID", keyColumn = "commentID")
    int insertComment(CommentBean commentBean);

    @Select("select commentID from Comment where userID = #{userID}")
    List<Integer> getAllCommentIDByUserID(int userID);

    @Select("select commentID from Comment where posterID = #{posterID}")
    List<Integer> getAllCommentIDByPosterID(int posterID);

    @Select("select commentID from Comment where parentCommentID = #{parentCommentID}")
    List<Integer> getAllCommentIDByParentCommentID(int parentCommentID);
}
