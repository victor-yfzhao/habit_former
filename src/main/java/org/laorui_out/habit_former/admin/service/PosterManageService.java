package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.PosterBean;

public interface PosterManageService extends IService<PosterBean> {
    IPage<PosterBean> selectAllPosters(int pageCount, int pageSize);
    int editPoster(PosterBean posterBean);
    int createPoster(PosterBean posterBean);
    int deletePoster(int posterID);
    int deletePosterByUserID(int userID);
}
