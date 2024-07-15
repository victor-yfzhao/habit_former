package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.CommentBean;
import org.laorui_out.habit_former.bean.PlanBean;
import org.laorui_out.habit_former.mapper.CommentMapper;
import org.laorui_out.habit_former.mapper.PlanMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentManageServiceImpl extends ServiceImpl<CommentMapper, CommentBean> implements CommentManageService{

    @Resource
    private CommentMapper commentMapper;
    @Override
    public int deleteComment(int commentID){return baseMapper.deleteById(commentID);}

    @Override
    public int deleteCommentByUserID(int userID) {
        List<Integer> comments = baseMapper.getAllCommentIDByUserID(userID);
        int cnt = 0;//影响的总行数(rows)
        for (int id : comments) {
            cnt += baseMapper.deleteById(id);
            List<Integer> subComments = baseMapper.getAllCommentIDByParentCommentID(id);
            for (int subID : subComments){
                cnt += baseMapper.deleteById(subID);
            }
        }
        return cnt;
    }

    @Override
    public int deleteCommentByPosterID(int posterID) {
        List<Integer> comments = baseMapper.getAllCommentIDByPosterID(posterID);
        int cnt = 0;//影响的总行数(rows)
        for (int id : comments) {
            cnt += baseMapper.deleteById(id);
        }
        return cnt;
    }

    public int deleteCommentByParentCommentID(int parentID){
        List<Integer> comments = baseMapper.getAllCommentIDByParentCommentID(parentID);
        int cnt = 0;//影响的总行数(rows)
        for (int id : comments) {
            cnt += baseMapper.deleteById(id);
        }
        return cnt;
    }

    public IPage<CommentBean> selectAllComments(Page<CommentBean> page) {
        return commentMapper.selectPage(page, null);
    }
}
