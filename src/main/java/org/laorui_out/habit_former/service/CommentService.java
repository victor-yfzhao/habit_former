package com.example.postarrangement.service;

import com.example.postarrangement.mapper.PosterMapper;
import org.springframework.stereotype.Service;

@Service
//对帖子进行评论
public class CommentService {
    private PosterMapper posterMapper;

    CommentService(PosterMapper posterMapper) {
        this.posterMapper = posterMapper;
    }
}
