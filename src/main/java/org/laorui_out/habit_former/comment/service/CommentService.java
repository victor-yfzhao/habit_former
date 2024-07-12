package org.laorui_out.habit_former.comment.service;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.CommentBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.CommentMapper;
import org.laorui_out.habit_former.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    UserMapper userMapper;
    //根据评论ID得到它所有的回复
    public List<CommentBean> getChildComment(int commentID){
        return commentMapper.getChildComment(commentID);
    }

    //根据评论ID获取评论
    public CommentBean getCommentByCommentID(int commentID){
        return commentMapper.getCommentByCommentID(commentID);
    }

    //根据帖子ID获取帖子中所有评论
    public List<CommentBean> getCommentByPosterID(int posterID){
        return commentMapper.getCommentByPosterID(posterID);
    }

    //根据评论ID获得它对应的用户的信息
    public UserBean getUserByCommentId(int commentID){ return userMapper.getUserByCommentId(commentID);}

}
