package org.laorui_out.habit_former.poster.service;

import org.laorui_out.habit_former.mapper.PosterMapper;
import org.springframework.stereotype.Service;

@Service
//搜索栏搜索查看帖子
public class SearchPosterService {
    private final PosterMapper posterMapper;

    SearchPosterService(PosterMapper posterMapper) {
        this.posterMapper = posterMapper;
    }
}
