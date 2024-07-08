package org.laorui_out.habit_former.service;

import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

@Service
//对帖子进行评论
public class CommentService {
    private PosterMapper posterMapper;

    CommentService(PosterMapper posterMapper) {
        this.posterMapper = posterMapper;
    }
}
