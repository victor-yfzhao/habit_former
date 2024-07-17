package org.laorui_out.habit_former.poster.controller;

import jakarta.annotation.Resource;
import org.laorui_out.habit_former.bean.LikesBean;
import org.laorui_out.habit_former.bean.PosterAndUserBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.poster.service.*;
import org.laorui_out.habit_former.user.service.ProfileService;
import org.laorui_out.habit_former.utils.ResponseMessage;
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
    @Resource
    ProfileService profileService;

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
                return new ResponseMessage<>(500,"错误","帖子对应的计划名称不存在，信息显示错误");
            }

            String planType = posterService.getPlanTypeByPosterId(posterID);
            if(planType == null){
                return new ResponseMessage<>(500,"错误","帖子对应的计划类型不存在，信息显示错误");
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
                    planType,
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
                return new ResponseMessage<>(200,"成功",isCreate);
            }
            return new ResponseMessage<>(500,"失败",isCreate);
        }
        catch (Exception e){
            return new ResponseMessage<>(500, "失败", e.getMessage());
        }
    }


    //返回封面全部帖子的缩略信息
    @GetMapping("poster/allparts")
    public ResponseMessage<List<Object>> getAllPosterParts(){
        try{
            List<PosterBean> posterBeanList = posterPictureService.getAllPosterWithPictures();
            if(posterBeanList == null){
                return new ResponseMessage<>(200,"帖子缩略信息返回", null);
            }else{
                return getResponseMessages(posterBeanList);
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,e.getMessage(),null);
        }
    }

    //返回对应计划类型的帖子的信息
    @GetMapping("poster/typeParts")
    public ResponseMessage<List<Object>> getPosterPartsByTypes(String planType){
        try{
            List<PosterBean> posterBeanList = posterPictureService.getPosterWithPicturesByTypes(planType);
            if(posterBeanList == null){
                return new ResponseMessage<>(200,"帖子缩略信息返回", null);
            }else{
                return getResponseMessages(posterBeanList);
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,e.getMessage(),null);
        }
    }



    //根据帖子的列表获取他们的ResponseMessage的列表
    private ResponseMessage<List<Object>> getResponseMessages(List<PosterBean> posterBeanList) {
        List<Object> posterMessages = new ArrayList<>();
        for(PosterBean posterBean : posterBeanList){
            Object posterBeanPartItem = posterService.getPosterParts(posterBean.getPosterID());
            posterMessages.add(posterBeanPartItem);
        }
        return new ResponseMessage<>(200,"帖子缩略信息返回",posterMessages);
    }

    //获取根据输入词查找到的所有帖子的缩略信息
    @GetMapping("poster/parts/searchWords")
    public ResponseMessage<List<Object>> getPosterWithWords(String searchWords){
        try{
            List<PosterBean> posterBeanList = searchPosterService.getPosterWithWordsAndPictrues(searchWords);
            if(posterBeanList == null){
                return new ResponseMessage<>(200,"帖子缩略信息返回", null);
            }else{
                return getResponseMessages(posterBeanList);
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,e.getMessage(),null);
        }
    }

    //根据posterID删除帖子
    @DeleteMapping("poster/deletePoster")
    public ResponseMessage<String> deletePoster(int posterID){
        try{
            boolean isPosterDelete = deletePosterService.deletePoster(posterID);
            if(isPosterDelete){
                return new ResponseMessage<>(200,"成功删除","成功删除");
            }
            else {
                return new ResponseMessage<>(500,"删除失败","帖子信息不存在，删除失败");
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,"删除失败",e.getMessage());
        }
    }

    //为帖子进行点赞
    @PostMapping("poster/likes")
    public ResponseMessage<String> addLikes(
            @RequestParam("userID") int userID,
            @RequestParam("posterID") int posterID
    ){
        try{
            if(!posterService.isInPosterIDList(posterID)){
                return new ResponseMessage<>(500,"错误","帖子ID不存在，点赞出现错误");
            }
            UserBean userBean = profileService.getProfile(userID);
            if(userBean == null){
                return new ResponseMessage<>(500,"错误","用户ID不存在，点赞出现错误");
            }
            LikesBean likesBean = new LikesBean(userID,posterID);
            String isAddLikes = posterService.addLikes(likesBean);
            if(Objects.equals(isAddLikes, "插入成功")){
                return new ResponseMessage<>(200,"成功",isAddLikes);
            }else{
                return new ResponseMessage<>(500,"失败",isAddLikes);
            }

        }catch(Exception e){
            return new ResponseMessage<>(500,"抛出错误",e.getMessage());
        }
    }

    //收藏帖子
    @PostMapping("poster/collection")
    public ResponseMessage<String> addCollection(
            @RequestParam("userID") int userID,
            @RequestParam("posterID") int posterID
    ){
        try{
            if(!posterService.isInPosterIDList(posterID)){
                return new ResponseMessage<>(500,"错误","帖子ID不存在，收藏出现错误");
            }
            UserBean userBean = profileService.getProfile(userID);
            if(userBean == null){
                return new ResponseMessage<>(500,"错误","用户ID不存在，收藏出现错误");
            }
            LikesBean likesBean = new LikesBean(userID,posterID);
            String isAddCollection = posterService.addCollection(likesBean);
            if(Objects.equals(isAddCollection, "插入成功")){
                return new ResponseMessage<>(200,"成功",isAddCollection);
            }else{
                return new ResponseMessage<>(500,"失败",isAddCollection);
            }

        }catch(Exception e){
            return new ResponseMessage<>(500,"抛出错误",e.getMessage());
        }
    }

    @DeleteMapping("poster/deleteLikes")
    public ResponseMessage<String> deleteLikes(int userID, int posterID){
        try{
            boolean isLikesDelete = posterService.deleteLikes(userID, posterID);
            if(isLikesDelete){
                return new ResponseMessage<>(200,"成功删除","成功删除");
            }
            else {
                return new ResponseMessage<>(500,"删除失败","点赞信息删除失败");
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,"删除失败",e.getMessage());
        }
    }

    @DeleteMapping("poster/deleteCollection")
    public ResponseMessage<String> deleteCollection(int userID, int posterID){
        try{
            boolean isCollectionDelete = posterService.deleteCollection(userID, posterID);
            if(isCollectionDelete){
                return new ResponseMessage<>(200,"成功删除","成功删除");
            }
            else {
                return new ResponseMessage<>(500,"删除失败","收藏信息删除失败");
            }
        }catch (Exception e){
            return new ResponseMessage<>(500,"删除失败",e.getMessage());
        }
    }



}



