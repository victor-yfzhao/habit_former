package org.laorui_out.habit_former.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.admin.service.*;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.user.service.LoginResult;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class AdminController {

    @Resource
    UserManageService userManageService;

//    @Resource
//    PosterManageService posterManageService;
//
//    @Resource
//    PlanManageService planManageService;

    @Value("${admin.password}")
    private String password;

    @GetMapping("/admin/login")
    public ResponseMessage<LoginResult> login(String password) {
        if (password.equals(this.password)) {
            return new ResponseMessage<>(200, "admin login success", LoginResult.SUCCESS);
        }
        return new ResponseMessage<>(400, "admin login failed", LoginResult.PASSWORD_ERROR);
    }

    //用户管理
    //TODO:用户新建和更改时，用户名重名的问题？谁负责检测，以及报错的处理
    //--新建用户
    @GetMapping("/admin/user/create")
    public ResponseMessage<UserBean> createUser(String userName,String password){
        UserBean userBean=new UserBean();
        userBean.setUserIcon(null);
        userBean.setUserCreateDate(new Date(System.currentTimeMillis()));
        userBean.setUsername(userName);
        userBean.setPassword(password);
        //TODO:userid 的自行创建与返回？
        userManageService.createUser(userBean);
        if(userBean.getUserID()!=null&&userBean.getUserID()>=0){
            return new ResponseMessage<>(200,"user create success",userBean);
        }
        return new ResponseMessage<>(400,"user create failed",null);
    }

    //--删除用户
    @GetMapping("/admin/user/delete")
    public ResponseMessage<Integer> deleteUser(int userID){
        int res = userManageService.deleteUser(userID);
        if(res == 1){
            return new ResponseMessage<>(200,"userID:"+userID+" delete success..",res);
        }
        return new ResponseMessage<>(400,"userID:"+userID+" delete failed..",res);
    }

    //--修改用户
    @GetMapping("/admin/user/edit")
    public ResponseMessage<UserBean> editUser(int userID,String userName,String password,String userIcon){
        UserBean userBean=new UserBean();
        userBean.setUserID(userID);
        userBean.setUserIcon(userIcon);
        userBean.setPassword(password);
        userBean.setUserCreateDate(new Date(System.currentTimeMillis()));
        int res=userManageService.updateUser(userBean);
        if(res == 1){
            return new ResponseMessage<>(200,"userID:"+userID+" edit success..",userBean);
        }
        return new ResponseMessage<>(400,"userID:"+userID+" edit failed..",null);
    }
    //--查询用户
    @GetMapping("/admin/user")
    public ResponseMessage<IPage<UserBean>> selectAllUsers(int pointer, int pageSize){
        try{
            Page<UserBean> page = new Page<>(pointer,pageSize);
            IPage<UserBean> userRecords = userManageService.selectAllUsers(page);
            return new ResponseMessage<>(200,"query success", userRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,"query failed",null);
        }
    }

    /*
    //帖子管理
    //--查询帖子
    //TODO:分页查询

    //--修改帖子
    //---修改内容
    @GetMapping("/admin/poster/edit/content")
    public ResponseMessage<PosterBean> editPosterContent(int posterID, String posterHeadline, String posterDetail){
        PosterBean posterBean=posterManageService.getById(posterID);
        posterBean.setPosterDetail(posterDetail);
        posterBean.setPosterHeadline(posterHeadline);
        int res = posterManageService.editPoster(posterBean);
        if(res!=0)
            return new ResponseMessage<>(200,"posterID:"+posterID+" content-edit-success",posterBean);
        return new ResponseMessage<>(400,"posterID:"+posterID+" content-edit-failed",null);
    }

    //---修改图片
    // TODO?传一个新的list还是在原有list上修改

    //--删除帖子
    @GetMapping("/admin/poster/delete")
    public ResponseMessage<Integer> deletePoster(int posterID){
        int res=posterManageService.deletePoster(posterID);
        if(res!=0)
            return new ResponseMessage<>(200,"posterID:"+posterID+" delete-success",res);
        return new ResponseMessage<>(400,"posterID:"+posterID+" delete-failed",res);

    }
    @GetMapping("/admin/poster/delete_all")
    public ResponseMessage<Integer> deleteAllPosterByUserID(int userID){
        int res = posterManageService.deletePosterByUserID(userID);//affected rows
        if(res!=0)
            return new ResponseMessage<>(200,"userID:"+userID+" posters-delete-success",res);
        return new ResponseMessage<>(400,"userID:"+userID+" posters-delete-failed",res);
    }
    //计划管理
    //API请求记录(如果要实现得新建表)
    //看板
    //--平台内每日计划完成总数目
    //--平台内每日完成了计划的用户数
    //--平台内每日帖子增加量
    //--每日计划完成的比例(按计划类型分类)
    //--帖子点赞量排名
    //--帖子收藏量排名
     */
}
