package com.example.postarrangement.service;

import com.example.postarrangement.mapper.PosterMapper;
import org.springframework.stereotype.Service;

@Service
//查看用户的所有帖子等内容
public class UserPosterManageService {
    private PosterMapper posterMapper;

    public UserPosterManageService(PosterMapper posterMapper) {
        this.posterMapper = posterMapper;
    }
}
