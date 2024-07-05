package org.laorui_out.habit_former.controller;

import org.laorui_out.habit_former.service.CommentService;
import org.laorui_out.habit_former.service.PosterService;
import org.laorui_out.habit_former.service.SearchPosterService;
import org.laorui_out.habit_former.service.UserPosterManageService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PosterController {
    private final CommentService commentService;
    private final PosterService posterService;
    private final SearchPosterService searchPosterService;
    private final UserPosterManageService userPosterManageService;


    public PosterController(CommentService commentService, PosterService posterService, SearchPosterService searchPosterService, UserPosterManageService userPosterManageService) {
        this.commentService = commentService;
        this.posterService = posterService;
        this.searchPosterService = searchPosterService;
        this.userPosterManageService = userPosterManageService;
    }
}
