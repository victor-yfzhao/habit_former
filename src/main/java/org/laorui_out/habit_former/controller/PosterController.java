package com.example.postarrangement.controller;

import com.example.postarrangement.service.CommentService;
import com.example.postarrangement.service.PosterService;
import com.example.postarrangement.service.SearchPosterService;
import com.example.postarrangement.service.UserPosterManageService;
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
