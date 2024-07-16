package org.laorui_out.habit_former.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.laorui_out.habit_former.admin.service.*;
import org.laorui_out.habit_former.admin.utils.CollectsRank;
import org.laorui_out.habit_former.admin.utils.LikesRank;
import org.laorui_out.habit_former.bean.*;
import org.laorui_out.habit_former.poster.service.PosterPictureService;
import org.laorui_out.habit_former.poster.service.PosterService;
import org.laorui_out.habit_former.user.service.LoginResult;
import org.laorui_out.habit_former.user.service.RegisterResult;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.laorui_out.habit_former.plan.constant.Constants.sdf;

@RestController
public class AdminController {

    @Resource
    UserManageService userManageService;

    @Resource
    PosterManageService posterManageService;

    @Resource
    PlanManageService planManageService;

    @Resource
    DashboardService dashboardService;

    @Resource
    LoginManageService loginManageService;

    @Resource
    CommentManageService commentManageService;

    @Resource
    PosterPictureService posterPictureService;

    @Resource
    PosterService posterService;

    @Value("${admin.password}")
    private String password;

    @Value("${admin.username}")
    private String username;

    /**
     * 管理员登录
     */
    @GetMapping("/admin/login")
    public ResponseMessage<LoginResult> login(String username, String password) {
        if (password.equals(this.password) && username.equals(this.username)) {
            return new ResponseMessage<>(200, "admin login success", LoginResult.SUCCESS);
        }
        return new ResponseMessage<>(400, "admin login failed", LoginResult.PASSWORD_ERROR);
    }


    /**
     * 登录日志模块
     */
    @GetMapping("/admin/login_log")
    public ResponseMessage<IPage<LoginBean>> loginLog(int pointer, int pageSize) {
        try{
            Page<LoginBean> page = new Page<>(pointer, pageSize);
            IPage<LoginBean> loginRecords = loginManageService.selectAllLog(page);
            return new ResponseMessage<>(200, "success", loginRecords);
        }catch (Exception e){
            return new ResponseMessage<>(400, e.getMessage(), null);
        }
    }


    /**
     * 用户管理
     */
    //--新建用户
    @PostMapping("/admin/user/create")
    public ResponseMessage<UserBean> createUser(String userName,String password){
        UserBean userBean = new UserBean();

        userBean.setUserIcon(null);
        userBean.setUserCreateDate(new Date(System.currentTimeMillis()));
        userBean.setUsername(userName);
        userBean.setPassword(password);

        RegisterResult result = userManageService.createUser(userBean);

        if(result == RegisterResult.SUCCESS){
            userBean = userManageService.selectUserByUsername(userName).get(0);
            return new ResponseMessage<>(200,"user create success",userBean);
        }
        return new ResponseMessage<>(400, result.toString(), null);
    }

    //--删除用户
    @PostMapping("/admin/user/delete")
    public ResponseMessage<Integer> deleteUser(int userID){
        planManageService.deletePlanByUserID(userID);
        posterManageService.deletePosterByUserID(userID);
        commentManageService.deleteCommentByUserID(userID);
        int res = userManageService.deleteUser(userID);
        if(res == 1){
            return new ResponseMessage<>(200,"userID:"+userID+" delete success..",res);
        }
        return new ResponseMessage<>(400,"userID:"+userID+" delete failed..",res);
    }

    //--修改用户
    @PostMapping("/admin/user/edit")
    public ResponseMessage<UserBean> editUser(@RequestBody UserBean userBean){
        int res = userManageService.updateUser(userBean);
        if(res == 1){
            return new ResponseMessage<>(200,"userID:"+userBean.getUserID()+" edit success..",userBean);
        }
        return new ResponseMessage<>(400,"userID:"+userBean.getUserID()+" edit failed..",null);
    }

    //--查询用户
    @GetMapping("/admin/user")
    public ResponseMessage<IPage<UserBean>> selectAllUsers(int pointer, int pageSize){
        try{
            Page<UserBean> page = new Page<>(pointer,pageSize);
            IPage<UserBean> userRecords = userManageService.selectAllUsers(page);
            return new ResponseMessage<>(200,"query success", userRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,e.getMessage(),null);
        }
    }


    /**
     * 帖子管理
     */
    //--查询帖子
    @GetMapping("/admin/poster")
    public ResponseMessage<IPage<PosterBean>> selectAllPosters(int pointer, int pageSize){
        try{
            Page<PosterBean> page = new Page<>(pointer,pageSize);
            IPage<PosterBean> posterRecords = posterManageService.selectAllPosters(page);
            for(PosterBean posterBean : posterRecords.getRecords()){
                posterBean.setPosterPicture(posterPictureService.getPosterPicturesByPosterId(posterBean.getPosterID()));
                posterBean.setNumOfLikes(posterService.getTotalLikes(posterBean.getPosterID()));
                posterBean.setNumOfCollections(posterService.getTotalCollection(posterBean.getPosterID()));
            }
            return new ResponseMessage<>(200,"query success", posterRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,e.getMessage(),null);
        }
    }

    //--修改帖子
    //---修改内容
    @PostMapping("/admin/poster/edit/content")
    public ResponseMessage<PosterBean> editPosterContent(@RequestBody PosterBean posterBean){
        int res = posterManageService.editPoster(posterBean);
        if(res!=0)
            return new ResponseMessage<>(200,"posterID:"+posterBean.getPosterID()+" content-edit-success",posterBean);
        return new ResponseMessage<>(400,"posterID:"+posterBean.getPosterID()+" content-edit-failed",null);
    }

    //---修改图片
    // TODO?传一个新的list还是在原有list上修改

    //--删除帖子
    @PostMapping("/admin/poster/delete")
    public ResponseMessage<Integer> deletePoster(int posterID){
        commentManageService.deleteCommentByPosterID(posterID);
        int res = posterManageService.deletePoster(posterID);
        if(res != 0)
            return new ResponseMessage<>(200,"posterID:"+posterID+" delete-success",res);
        return new ResponseMessage<>(400,"posterID:"+posterID+" delete-failed",res);

    }

    @PostMapping("/admin/poster/delete_all")
    public ResponseMessage<Integer> deleteAllPosterByUserID(int userID){
        int res = posterManageService.deletePosterByUserID(userID);//affected rows
        if(res!=0)
            return new ResponseMessage<>(200,"userID:"+userID+" posters-delete-success",res);
        return new ResponseMessage<>(400,"userID:"+userID+" posters-delete-failed",res);
    }

    /**
     * 计划管理
     */
    //--查询计划
    @GetMapping("/admin/plan")
    public ResponseMessage<IPage<PlanBean>> selectAllPlans(int pointer, int pageSize){
        try{
            Page<PlanBean> page = new Page<>(pointer,pageSize);
            IPage<PlanBean> planRecords = planManageService.selectAllPlans(page);
            for(PlanBean item:planRecords.getRecords()){
                item.setPlanDateShow(sdf.format(item.getPlanDate()));
            }
            return new ResponseMessage<>(200,"query success", planRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,e.getMessage(),null);
        }
    }

    @GetMapping("/admin/daily_plan")
    public ResponseMessage<IPage<DailyPlanBean>> selectAllDailyPlans(int pointer, int pageSize){
        try{
            Page<DailyPlanBean> page = new Page<>(pointer,pageSize);
            IPage<DailyPlanBean> planRecords = planManageService.selectAllDailyPlans(page);
            return new ResponseMessage<>(200,"query success", planRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,"query failed",null);
        }
    }

    @GetMapping("/admin/fit_plan")
    public ResponseMessage<IPage<FitPlanBean>> selectAllFitPlans(int pointer, int pageSize){
        try{
            Page<FitPlanBean> page = new Page<>(pointer,pageSize);
            IPage<FitPlanBean> planRecords = planManageService.selectAllFitPlans(page);
            return new ResponseMessage<>(200,"query success", planRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,"query failed",null);
        }
    }

    @GetMapping("/admin/study_plan")
    public ResponseMessage<IPage<StudyPlanBean>> selectAllStudyPlans(int pointer, int pageSize){
        try{
            Page<StudyPlanBean> page = new Page<>(pointer,pageSize);
            IPage<StudyPlanBean> planRecords = planManageService.selectAllStudyPlans(page);
            return new ResponseMessage<>(200,"query success", planRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,"query failed",null);
        }
    }

    //--删除计划
    @PostMapping("/admin/plan/delete")
    public ResponseMessage<Integer> deletePlan(int planID){
        int res = planManageService.deletePlan(planID);
        if(res>0)
            return new ResponseMessage<>(200,"planID:"+planID+" delete-success",res);
        return new ResponseMessage<>(400,"planID:"+planID+" delete-failed",res);

    }
    @PostMapping("/admin/plan/delete_all")
    public ResponseMessage<Integer> deleteAllPlanByUserID(int userID){
        int res = planManageService.deletePlanByUserID(userID);//affected rows
        if(res>0)
            return new ResponseMessage<>(200,"userID:"+userID+" plans-delete-success",res);
        return new ResponseMessage<>(400,"userID:"+userID+" plans-delete-failed",res);
    }
    @PostMapping("/admin/daily_plan/delete")
    public ResponseMessage<Integer> deleteDailyPlan(int dailyPlanID){
        int res = planManageService.deleteDailyPlan(dailyPlanID);
        if(res>0)
            return new ResponseMessage<>(200,"dailyPlanID:"+dailyPlanID+" delete-success",res);
        return new ResponseMessage<>(400,"dailyPlanID:"+dailyPlanID+" delete-failed",res);

    }
    @PostMapping("/admin/fit_plan/delete")
    public ResponseMessage<Integer> deleteFitPlan(int fitPlanID){
        int res = planManageService.deleteFitPlan(fitPlanID);
        if(res>0)
            return new ResponseMessage<>(200,"fitPlanID:"+fitPlanID+" delete-success",res);
        return new ResponseMessage<>(400,"fitPlanID:"+fitPlanID+" delete-failed",res);

    }
    @PostMapping("/admin/study_plan/delete")
    public ResponseMessage<Integer> deleteStudyPlan(int studyPlanID){
        int res = planManageService.deleteStudyPlan(studyPlanID);
        if(res>0)
            return new ResponseMessage<>(200,"studyPlanID:"+studyPlanID+" delete-success",res);
        return new ResponseMessage<>(400,"studyPlanID:"+studyPlanID+" delete-failed",res);

    }
    //--修改计划
    @PostMapping("/admin/plan/edit")
    public ResponseMessage<PlanBean> editPlan(PlanBean item){
        PlanBean res;
        if(item.getPlanID()==null){
            res=planManageService.addPlan(item.getPlanName(),item.getPlanInfo(),item.getUserID(),item.getPlanType());
            res.setPlanDateShow(sdf.format(res.getPlanDate()));
            return new ResponseMessage<>(200,"success",res);
        }
        if(planManageService.updatePlan(item)!=0)
            return new ResponseMessage<>(200,"success",item);
        else return new ResponseMessage<>(400,"failed",null);
    }
    @PostMapping("/admin/dailyplan/edit")
    public ResponseMessage<DailyPlanBean> editDailyPlan(DailyPlanBean item){
        DailyPlanBean res;
        if(item.getDailyPlanID()==null){
            res=planManageService.addDailyPlan(Date.valueOf(item.getDateShow()),item.getPlanDetail(),item.getPlanID());
            res.setDateShow(sdf.format(res.getDate()));
            return new ResponseMessage<>(200,"success",res);
        }
        if(planManageService.updateDailyPlan(item)!=0)
            return new ResponseMessage<>(200,"success",item);
        else return new ResponseMessage<>(400,"failed",null);
    }
    @PostMapping("/admin/fitplan/edit")
    public ResponseMessage<FitPlanBean> editFitPlan(FitPlanBean item){
        FitPlanBean res;
        if(item.getFitPlanItemID()==null){
            res=planManageService.addFitPlan(
                    Date.valueOf(item.getDateShow()),
                    item.getFitItemName(),
                    item.getFitType(),
                    item.getGroupNum(),
                    item.getNumPerGroup(),
                    item.getTimePerGroup(),
                    item.getPlanID());
            res.setDateShow(sdf.format(res.getDate()));
            return new ResponseMessage<>(200,"success",res);
        }
        if(planManageService.updateFitPlan(item)!=0)
            return new ResponseMessage<>(200,"success",item);
        else return new ResponseMessage<>(400,"failed",null);
    }
    @PostMapping("/admin/studyplan/edit")
    public ResponseMessage<StudyPlanBean> editPlan(StudyPlanBean item){
        StudyPlanBean res;
        if(item.getStudyPlanItemID()==null){
            res=planManageService.addStudyPlan(Date.valueOf(item.getDateShow()),item.getStudySubject(),item.getStudyContent(),item.getStudyTime(),item.getPlanID());
            res.setDateShow(sdf.format(res.getDate()));
            return new ResponseMessage<>(200,"success",res);
        }
        if(planManageService.updateStudyPlan(item)!=0)
            return new ResponseMessage<>(200,"success",item);
        else return new ResponseMessage<>(400,"failed",null);
    }

    //API请求记录(如果要实现得新建表)

    /**
     * 评论管理
     */
    //根据评论ID删除评论
    @PostMapping("/admin/comment/delete")
    public ResponseMessage<Integer> deleteComment(int commentID){
        int res = commentManageService.deleteComment(commentID);
        int resChild = commentManageService.deleteCommentByParentCommentID(commentID);
        if(res + resChild >0)
            return new ResponseMessage<>(200,"commentID:"+commentID+" delete-success", res);
        return new ResponseMessage<>(400,"commentID:"+commentID+" delete-failed", res);
    }

    @GetMapping("/admin/comment")
    public ResponseMessage<IPage<CommentBean>> selectAllComments(int pointer, int pageSize){
        try{
            Page<CommentBean> page = new Page<>(pointer,pageSize);
            IPage<CommentBean> commentRecords = commentManageService.selectAllComments(page);
            return new ResponseMessage<>(200,"query success", commentRecords);
        }catch(Exception e){
            return new ResponseMessage<>(400,e.getMessage(),null);
        }
    }

    @PostMapping("/admin/comment/edit")
    public ResponseMessage<CommentBean> modifyComment(@RequestBody CommentBean commentBean){
        int res = commentManageService.modifyComment(commentBean);
        commentBean = commentManageService.getById(commentBean.getCommentID());
        if(res == 1)
            return new ResponseMessage<>(200,"commentID:"+commentBean.getCommentID()+" modify-success", commentBean);
        return new ResponseMessage<>(400,"commentID:"+commentBean.getCommentID()+" modify-failed", commentBean);
    }

    /**
     * 看板
     */
    //--平台内每日计划完成总数目
    //返回一周的数据
    @GetMapping("/admin/dashboard/finished_plan")
    public ResponseMessage<Map<String, Integer>> getFinishedPlanCount(){
        Map<String, Integer> res = dashboardService.countDailyFinishedPlanItem();
        return new ResponseMessage<>(200,"query success",res);
    }

    //--平台内每日完成了计划的用户数
    //返回一周的数据
    @GetMapping("/admin/dashboard/finished_plan_user")
    public ResponseMessage<Map<String, Integer>> getFinishedPlanUserCount(){
        Map<String, Integer> res = dashboardService.countDailyFinishedUser();
        return new ResponseMessage<>(200,"query success",res);
    }

    //--平台内每日帖子增加量
    //返回一周的数据
    @GetMapping("/admin/dashboard/added_post")
    public ResponseMessage<Map<String, Integer>> getAddedPostCount(){
        Map<String, Integer> res = dashboardService.countDailyAddedPost();
        return new ResponseMessage<>(200,"query success",res);
    }

    //--每日计划完成的比例(按计划类型分类)
    //返回当天的
    @GetMapping("/admin/dashboard/finished_plan_by_type")
    public ResponseMessage<Map<String, Integer>> getFinishedPlanCountByType(){
        Map<String, Integer> res = dashboardService.countDailyFinishedPlanItemByType();
        return new ResponseMessage<>(200,"query success",res);
    }

    //--帖子点赞量排名
    //返回当天的
    @GetMapping("/admin/dashboard/post_like_ranking")
    public ResponseMessage<List<LikesRank>> getPostLikeRanking(int rankSize){
        List<LikesRank> res = dashboardService.countPostLikeRanking(rankSize);
        return new ResponseMessage<>(200,"query success",res);
    }

    //--帖子收藏量排名
    //返回当天的
    @GetMapping("/admin/dashboard/post_collect_ranking")
    public ResponseMessage<List<CollectsRank>> getPostCollectRanking(int rankSize){
        List<CollectsRank> res = dashboardService.countPostCollectRanking(rankSize);
        return new ResponseMessage<>(200,"query success",res);
    }

}
