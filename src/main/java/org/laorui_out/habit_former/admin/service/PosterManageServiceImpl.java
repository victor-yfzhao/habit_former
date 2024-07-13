package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PosterManageServiceImpl extends ServiceImpl<PosterMapper, PosterBean> implements PosterManageService {
    @Override
    public IPage<PosterBean> selectAllPosters(Page<PosterBean> page) {
        return baseMapper.selectPage(page, null);
    }

    //编辑帖子内容
    @Override
    public int editPoster(PosterBean posterBean) {
        return baseMapper.updateById(posterBean);
    }

    //新建帖子
    @Override
    public int createPoster(PosterBean posterBean) {
        return baseMapper.insertPoster(posterBean);
    }

    //删除指定id的帖子
    @Override
    public int deletePoster(int posterID) {
        return baseMapper.deleteById(posterID);
    }

    //删除指定用户的所有帖子
    @Override
    public int deletePosterByUserID(int userID) {
        List<Integer> posters = baseMapper.getAllPosterIDByUserID(userID);
        int cnt = 0;//影响的总行数(rows)
        for (int id : posters) {
            cnt += baseMapper.deleteById(id);
        }
        return cnt;
    }
}
