package org.laorui_out.habit_former.poster.controller;

import org.laorui_out.habit_former.bean.PosterAndUserBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.poster.service.*;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class PosterController {

    private final PosterService posterService;
    private final SearchPosterService searchPosterService;
    private final CreatePosterService createPosterService;
    private final PosterPictureService posterPictureService;
    private final DeletePosterService deletePosterService;



    @Autowired
    public PosterController(PosterService posterService, SearchPosterService searchPosterService, CreatePosterService createPosterService, PosterPictureService posterPictureService, DeletePosterService deletePosterService) {
        this.posterService = posterService;
        this.searchPosterService = searchPosterService;
        this.createPosterService = createPosterService;
        this.posterPictureService = posterPictureService;
        this.deletePosterService = deletePosterService;
    }



    //根据帖子ID，展示一个帖子的所有信息，包括用户信息和帖子信息
    @GetMapping("poster/details")
    public ResponseMessage<PosterAndUserBean> getPosterDetails(
            @RequestParam("posterID") int posterID) {

        // Get user details
        UserBean userBean = posterService.getUserByPosterId(posterID);
        // Get poster details with pictures
        PosterBean poster = posterPictureService.getPosterWithPictures(posterID);

        String planName = posterService.getPlanNameByPosterId(posterID);

        int numOfLikes = posterService.getTotalLikes(posterID);
        System.out.println("点赞数：");
        System.out.println(numOfLikes);

        int numOfCollection = posterService.getTotalCollection(posterID);
        System.out.println("收藏数：");
        System.out.println(numOfCollection);

        // 为这个类对象赋值
        PosterAndUserBean posterAndUserBean = new PosterAndUserBean();
        posterAndUserBean.setUserID(userBean.getUserID());
        posterAndUserBean.setUsername(userBean.getUsername());
        posterAndUserBean.setUserIcon(userBean.getUserIcon());
        posterAndUserBean.setPosterID(poster.getPosterID());
        posterAndUserBean.setPosterHeadline(poster.getPosterHeadline());
        posterAndUserBean.setPosterPicture(poster.getPosterPicture());
        posterAndUserBean.setPosterDetail(poster.getPosterDetail());
        posterAndUserBean.setPosterDate(poster.getPosterDate());
        posterAndUserBean.setPlanID(poster.getPlanID());
        posterAndUserBean.setPlanName(planName);
        posterAndUserBean.setNumOfLikes(numOfLikes);
        posterAndUserBean.setNumOfCollections(numOfCollection);

        ResponseMessage<PosterAndUserBean> responseMessage = new ResponseMessage<PosterAndUserBean>();
        responseMessage.setCode(200);
        responseMessage.setMessage("成功显示");
        responseMessage.setData(posterAndUserBean);
        return responseMessage;

    }

    //创建帖子
    @PostMapping("poster/createPoster")
    public ResponseMessage<String> createPoster(@RequestBody Map<String,Object> map)
    {   List<String> pictureList = (List< String>)map.get("posterPicture");
        String isCreate = createPosterService.createPoster((int)map.get("userID"), (int)map.get("planID"), (String) map.get("posterHeadline"), pictureList, (String) map.get("posterDetail"));
        if(Objects.equals(isCreate, "上传成功")){
            return new ResponseMessage<String>(200,"成功",isCreate);
        }
        return new ResponseMessage<String>(400,"失败",isCreate);
    }

    //根据ID返回缩略信息
    @GetMapping("poster/parts/searchID")
    public Object getPosterParts(
            @RequestParam("posterID") int posterID) {

        //获取用户信息
        UserBean userBean = posterService.getUserByPosterId(posterID);

        //获取帖子信息
        PosterBean poster = posterPictureService.getPosterWithPictures(posterID);

        //返回一个匿名类对象
        if(poster.getPosterPicture()!=null){
            System.out.println("列表的长度:");
            System.out.println(poster.getPosterPicture().size());
            for(int i =0;i<poster.getPosterPicture().size();i++){
                System.out.println(poster.getPosterPicture().get(i));
            }
            return new Object() {
                public final int userID = userBean.getUserID();
                public final String username = userBean.getUsername();
                public final String userIcon = userBean.getUserIcon();
                public final int posterID = poster.getPosterID();
                public final String posterHeadline = poster.getPosterHeadline();
                //主要看这里传参的数据类型
                //public final String posterPicture = String.valueOf(poster.getPosterPicture().stream().findFirst());
                public final String posterPicture = poster.getPosterPicture().get(0);
            };
        }
        else{
            return "图片不能为空";
        }
    }

    //返回封面全部帖子的缩略信息
    @GetMapping("poster/allparts")
    public List<ResponseMessage<Object>> getAllPosterParts(){
        List<PosterBean> posterBeanList = posterPictureService.getAllPosterWithPictures();
        return getResponseMessages(posterBeanList);
    }

    private List<ResponseMessage<Object>> getResponseMessages(List<PosterBean> posterBeanList) {
        List<ResponseMessage<Object>> posterMessages = new ArrayList<>();
        for(PosterBean posterBean : posterBeanList){
            Object posterBeanPartItem = getPosterParts(posterBean.getPosterID());
            posterMessages.add(new ResponseMessage<Object>(200,"显示成功",posterBeanPartItem));
        }
        return posterMessages;
    }

    @GetMapping("poster/parts/searchWords")
    public List<ResponseMessage<Object>> getPosterWithWords(String searchWords){
        List<PosterBean> posterBeanList = searchPosterService.getPosterWithWordsAndPictrues(searchWords);
        System.out.println("此时的posterBeanList");
        System.out.println(posterBeanList);
        return getResponseMessages(posterBeanList);
    }

    @DeleteMapping("poster/deletePoster")
    public ResponseMessage<String> deletePoster(int posterID){
        boolean isPosterDelete = deletePosterService.deletePoster(posterID);
        if(isPosterDelete){
            return new ResponseMessage<String>(200,"成功删除","成功删除");
        }
        else {
            return new ResponseMessage<String>(400,"删除失败","删除失败");
        }
    }


}



