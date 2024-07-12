package org.laorui_out.habit_former.comment.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.CommentBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.CommentMapper;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateCommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PosterMapper posterMapper;

    public String createComment(int userID, int posterID, int parentCommentID, String commentDetail){
        List<CommentBean> replies = new ArrayList<>();
        UserBean userTestBean = userMapper.getUserProfileThroughID(userID);
        if(userTestBean == null){
            return "不存在对应的用户，评论创建失败";
        }
        PosterBean posterTestBean = posterMapper.getPosterById(posterID);
        if(posterTestBean == null){
            return "不存在对应的帖子，评论创建失败";
        }
        CommentBean parentCommentTestBean = commentMapper.getCommentByCommentID(parentCommentID);
        if(parentCommentID > 0 && parentCommentTestBean == null){
            return "不存在父评论，评论创建失败";
        }
        if(commentDetail == null || commentDetail.isEmpty()){
            return "评论不能为空，评论创建失败";
        }
        CommentBean commentBean = new CommentBean(-100,commentDetail,LocalDate.now(),LocalTime.now(),userID,posterID,parentCommentID,replies);
        commentBean.setUserID(userID);
        commentBean.setPosterID(posterID);
        commentBean.setParentCommentID(parentCommentID);
        commentBean.setCommentDetail(commentDetail);
        commentBean.setCommentDate(LocalDate.now());
        commentBean.setCommentTime(LocalTime.now());
        int isInsert = commentMapper.insertComment(commentBean);
        if(isInsert <= 0){
            return "评论无法插入，评论创建失败";
        }
        return "创建成功";

    }

}
