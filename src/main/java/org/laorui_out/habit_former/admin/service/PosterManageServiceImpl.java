package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.mapper.PosterMapper;
import org.laorui_out.habit_former.poster.service.PosterPictureService;
import org.laorui_out.habit_former.poster.service.PosterService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PosterManageServiceImpl extends ServiceImpl<PosterMapper, PosterBean> implements PosterManageService {

    @Resource
    PosterMapper posterMapper;
    @Resource
    PosterService posterService;
    @Resource
    PosterPictureService posterPictureService;

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

    //删除指定id的帖子, cite laorui
    @Override
    public boolean deletePoster(int posterID) {
        //对于输入posterID是否存在的判断
        PosterBean posterTestBean = posterPictureService.getPosterWithPictures(posterID);
        Boolean deleteLikes = posterService.deleteLikesByPosterID(posterID);
        Boolean deleteCollection = posterService.deleteCollectionByPosterID(posterID);
        if(posterTestBean == null){
            return posterMapper.deletePosterByPosterId(posterID);
        }
        if(posterTestBean.getPosterPicture()==null || posterTestBean.getPosterPicture().isEmpty()){
            return posterMapper.deletePosterByPosterId(posterID);
        }else{
            // 删除 posterpicture 表中的记录
            boolean pictureDeleted = posterMapper.deletePosterPictureByPosterId(posterID);
            // 删除 poster 表中的记录
            boolean posterDeleted = posterMapper.deletePosterByPosterId(posterID);
            return posterDeleted && pictureDeleted;
        }
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
