package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.laorui_out.habit_former.bean.LoginBean;

public interface LoginManageService extends IService<LoginBean> {
    IPage<LoginBean> selectAllLog(Page<LoginBean> page);
}
