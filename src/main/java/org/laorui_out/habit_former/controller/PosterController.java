package org.laorui_out.habit_former.controller;

import org.laorui_out.habit_former.bean.PosterAndUserBean;
import org.laorui_out.habit_former.bean.PosterBean;
import org.laorui_out.habit_former.bean.UserBean;
import org.laorui_out.habit_former.service.CommentService;
import org.laorui_out.habit_former.service.PosterService;
import org.laorui_out.habit_former.service.SearchPosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PosterController {
    private final CommentService commentService;
    private final PosterService posterService;
    private final SearchPosterService searchPosterService;


    @Autowired
    public PosterController(CommentService commentService, PosterService posterService, SearchPosterService searchPosterService) {
        this.commentService = commentService;
        this.posterService = posterService;
        this.searchPosterService = searchPosterService;
    }


    @GetMapping("poster/findAll")
    public List<PosterBean> getAll(){
        List list = posterService.getAll();
        return list;
    }


    //展示一个帖子的所有信息，包括用户信息和帖子信息
    @GetMapping("poster/findPosterAndUser")
    public PosterAndUserBean getPosterDetails(
            @RequestParam("posterID") int posterID) {

        // Get user details
        UserBean userBean = posterService.getUserByPosterId(posterID);

        // Get poster details with pictures
        PosterBean poster = posterService.getPosterWithPictures(posterID);

        // Construct response object
        PosterAndUserBean posterAndUserBean = new PosterAndUserBean();
        posterAndUserBean.setUserID(userBean.getUserID());
        posterAndUserBean.setUsername(userBean.getUsername());
        posterAndUserBean.setUserIcon(userBean.getUserIcon());
        posterAndUserBean.setPosterID(poster.getPosterID());
        posterAndUserBean.setPosterHeadline(poster.getPosterHeadline());
        posterAndUserBean.setPosterPicture(poster.getPosterPicture());
        posterAndUserBean.setPosterDetail(poster.getPosterDetail());

        return posterAndUserBean;
    }
}



