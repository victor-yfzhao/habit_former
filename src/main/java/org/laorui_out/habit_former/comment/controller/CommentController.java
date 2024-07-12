package org.laorui_out.habit_former.comment.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.*;
import org.laorui_out.habit_former.comment.service.CommentService;
import org.laorui_out.habit_former.comment.service.CreateCommentService;
import org.laorui_out.habit_former.poster.service.PosterService;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects; 
  
  

@RestController
public class CommentController {
    @Resource
    CreateCommentService createCommentService;
    @Resource
    CommentService commentService;
    @Resource
    PosterService posterService;
    @PostMapping("comment/createComment")
    public ResponseMessage<String> createComment(@RequestBody Map<String,Object> map)
    {
        try{
            String isCommentCreate = createCommentService.createComment((int)map.get("userID"), (int)map.get("posterID"), (int) map.get("parentCommentID"), (String) map.get("commentDetail"));
            if(Objects.equals(isCommentCreate, "创建成功")){
                return new ResponseMessage<String>(200,"成功",isCommentCreate);
            }
            return new ResponseMessage<String>(500,"失败",isCommentCreate);
        }
        catch (Exception e){
            return new ResponseMessage<>(500, "失败", e.getMessage());
        }
    }

    @GetMapping("comment/showReply")
    public ResponseMessage<List> getReply(@RequestParam("commentID") int commentID){
        List<CommentAndUserBean> replyDetailList = new ArrayList<>();
        try{
            CommentBean commentBean = commentService.getCommentByCommentID(commentID);
            if(commentBean == null){
                return new ResponseMessage<>(500,"父评论ID不存在，信息查找错误",null);
            }
            List<CommentBean> childCommentList = commentService.getChildComment(commentID);
            return getListResponseMessage(replyDetailList, childCommentList);
        }catch (Exception e){
            return new ResponseMessage<>(500,"出现错误"+e.getMessage(),null);
        }
    }

    private ResponseMessage<List> getListResponseMessage(List<CommentAndUserBean> replyDetailList, List<CommentBean> childCommentList) {
        if(!childCommentList.isEmpty()){
            for(CommentBean childCommentBean:childCommentList){
                UserBean userBean = commentService.getUserByCommentId(childCommentBean.getCommentID());
                CommentAndUserBean commentAndUserBean = new CommentAndUserBean(
                        childCommentBean.getCommentID(),
                        childCommentBean.getCommentDetail(),
                        childCommentBean.getCommentDate(),
                        childCommentBean.getCommentTime(),
                        childCommentBean.getParentCommentID(),
                        userBean.getUserID(),
                        userBean.getUsername(),
                        userBean.getUserIcon()
                );
                replyDetailList.add(commentAndUserBean);
            }
        }
        return new ResponseMessage<>(200,"成功",replyDetailList);
    }

    @GetMapping("comment/showComment")
    public ResponseMessage<List> getComment(@RequestParam("posterID") int posterID){
        if(!posterService.isInPosterIDList(posterID)){
            return new ResponseMessage<>(200,"不存在对应的帖子，评论查找失败",null);
        }
        List<CommentAndUserBean> commentDetailList = new ArrayList<>();
        try{
            List<CommentBean>commentList = commentService.getCommentByPosterID(posterID);
            return getListResponseMessage(commentDetailList, commentList);

        }catch(Exception e){
            return new ResponseMessage<>(500, "失败:"+e.getMessage() ,null);
        }
    }

}





