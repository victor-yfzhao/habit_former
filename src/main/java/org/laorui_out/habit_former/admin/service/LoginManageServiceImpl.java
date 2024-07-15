package org.laorui_out.habit_former.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.laorui_out.habit_former.bean.LoginBean;
import org.laorui_out.habit_former.mapper.LoginMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginManageServiceImpl extends ServiceImpl<LoginMapper, LoginBean> implements LoginManageService {
    @Override
    public IPage<LoginBean> selectAllLog(Page<LoginBean> page) {
        return baseMapper.selectPage(page, null);
    }
}
