package com.example.postarrangement.service;

import com.example.postarrangement.mapper.PosterMapper;
import org.springframework.stereotype.Service;

@Service
//搜索栏搜索查看帖子
public class SearchPosterService {
    private PosterMapper posterMapper;

    SearchPosterService(PosterMapper posterMapper) {
        this.posterMapper = posterMapper;
    }
}
