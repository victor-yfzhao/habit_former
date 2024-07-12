package org.laorui_out.habit_former.poster.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.PosterAndUserBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.poster.service.*;
import org.laorui_out.habit_former.utils.ResponseMessage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class PosterController {
    @Resource
    PosterService posterService;
    @Resource
    SearchPosterService searchPosterService;
    @Resource
    CreatePosterService createPosterService;
    @Resource
    PosterPictureService posterPictureService;
    @Resource
    DeletePosterService deletePosterService;

    //根据帖子ID，展示一个帖子的所有信息，包括用户信息和帖子信息
    @GetMapping("poster/details")
    public ResponseMessage<Object> getPosterDetails(
            @RequestParam("posterID") int posterID) {

        try{
            if(!posterService.isInPosterIDList(posterID)){
                return new ResponseMessage<>(500,"错误","帖子ID不存在，信息显示错误");
            }
            // Get user details
            UserBean userBean = posterService.getUserByPosterId(posterID);
            if(userBean == null){
                return new ResponseMessage<>(500,"错误","帖子对应的用户信息不存在，信息显示错误");
            }
            // Get poster details with pictures
            PosterBean poster = posterPictureService.getPosterWithPictures(posterID);
            if(poster.getPosterPicture() == null){
                return new ResponseMessage<>(500,"错误","帖子对应的图片信息不存在，信息显示错误");
            }

            String planName = posterService.getPlanNameByPosterId(posterID);
            if(planName == null){
                return new ResponseMessage<>(500,"错误","帖子对应的计划不存在，信息显示错误");
            }

            int numOfLikes = posterService.getTotalLikes(posterID);

            int numOfCollection = posterService.getTotalCollection(posterID);

            // 为这个类对象赋值
            PosterAndUserBean posterAndUserBean = new PosterAndUserBean(
                    userBean.getUserID(),
                    userBean.getUsername(),
                    userBean.getUserIcon(),
                    poster.getPosterID(),
                    poster.getPosterHeadline(),
                    poster.getPosterPicture(),
                    poster.getPosterDetail(),
                    poster.getPosterDate(),
                    poster.getPlanID(),
                    planName,
                    numOfLikes,
                    numOfCollection);
            return new ResponseMessage<>(200,"成功显示",posterAndUserBean);

        }catch (Exception e){
            return new ResponseMessage<>(500,"失败",e.getMessage());
        }

    }


    //创建帖子
    @PostMapping("poster/createPoster")
    public ResponseMessage<String> createPoster(@RequestBody Map<String,Object> map)
    {
        try{
            List<String> pictureList = (List< String>)map.get("posterPicture");
            String isCreate = createPosterService.createPoster((int)map.get("userID"), (int)map.get("planID"), (String) map.get("posterHeadline"), pictureList, (String) map.get("posterDetail"));
            System.out.println("现在的isCreate是：" + isCreate);
            if(Objects.equals(isCreate, "创建成功")){
                return new ResponseMessage<String>(200,"成功",isCreate);
            }
            return new ResponseMessage<String>(500,"失败",isCreate);
        }
        catch (Exception e){
            return new ResponseMessage<>(500, "失败", e.getMessage());
        }
    }


    //返回封面全部帖子的缩略信息
    @GetMapping("poster/allparts")
    public ResponseMessage<List> getAllPosterParts(){
        List<String> errorMessage = new ArrayList<>();
        try{
            List<PosterBean> posterBeanList = posterPictureService.getAllPosterWithPictures();
            if(posterBeanList == null){
                return new ResponseMessage<>(200,"帖子缩略信息返回", null);
            }else{
                return getResponseMessages(posterBeanList);
            }
        }catch (Exception e){
            errorMessage.add(e.getMessage());
            return new ResponseMessage<>(500,"失败",errorMessage);
        }
    }

//    @GetMapping("poster/Userparts")
//    public ResponseMessage<List> getAllPosterParts(){
//        List<String> errorMessage = new ArrayList<>();
//        try{
//            List<PosterBean> posterBeanList = posterPictureService.getAllPosterWithPictures();
//            if(posterBeanList == null){
//                return new ResponseMessage<>(200,"帖子缩略信息返回", null);
//            }else{
//                return getResponseMessages(posterBeanList);
//            }
//        }catch (Exception e){
//            errorMessage.add(e.getMessage());
//            return new ResponseMessage<>(500,"失败",errorMessage);
//        }
//    }
//    getPosterWithPicturesByUserID






    //根据帖子的列表获取他们的ResponseMessage的列表
    private ResponseMessage<List> getResponseMessages(List<PosterBean> posterBeanList) {
        List<Object> posterMessages = new ArrayList<>();
        for(PosterBean posterBean : posterBeanList){
            Object posterBeanPartItem = posterService.getPosterParts(posterBean.getPosterID());
            posterMessages.add(posterBeanPartItem);
        }
        return new ResponseMessage<>(200,"帖子缩略信息返回",posterMessages);
    }

    //获取根据输入词查找到的所有帖子的缩略信息
    @GetMapping("poster/parts/searchWords")
    public ResponseMessage<List> getPosterWithWords(String searchWords){
        List<String> errorMessage = new ArrayList<>();
        try{
            List<PosterBean> posterBeanList = searchPosterService.getPosterWithWordsAndPictrues(searchWords);
            if(posterBeanList == null){
                return new ResponseMessage<>(200,"帖子缩略信息返回", null);
            }else{
                return getResponseMessages(posterBeanList);
            }
        }catch (Exception e){
            errorMessage.add(e.getMessage());
            return new ResponseMessage<>(500,"失败",errorMessage);
        }
//        List<PosterBean> posterBeanList = searchPosterService.getPosterWithWordsAndPictrues(searchWords);
//        if(posterBeanList == null){
//            return new ResponseMessage<>(200,"帖子缩略信息返回", null);
//        }
//        return getResponseMessages(posterBeanList);
    }

    //根据posterID删除帖子
    @DeleteMapping("poster/deletePoster")
    public ResponseMessage<String> deletePoster(int posterID){
        try{
            boolean isPosterDelete = deletePosterService.deletePoster(posterID);
            if(isPosterDelete){
                return new ResponseMessage<String>(200,"成功删除","成功删除");
            }
            else {
                return new ResponseMessage<String>(500,"删除失败","帖子信息不存在，删除失败");
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,"删除失败",e.getMessage());
        }

    }


}



