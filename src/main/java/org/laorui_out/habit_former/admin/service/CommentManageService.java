package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.CommentBean;
import org.laorui_out.habit_former.bean.StudyPlanBean;

public interface CommentManageService extends IService<CommentBean> {
    int deleteComment(int commentID);
    int deleteCommentByUserID(int userID);
    int deleteCommentByPosterID(int posterID);
    int deleteCommentByParentCommentID(int posterID);
    IPage<CommentBean> selectAllComments(Page<CommentBean> page);
}
