package org.laorui_out.habit_former.bean;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PosterBean {

    private int posterID;
    private String posterHeadline;
    private List<String> posterPicture;
    private String posterDetail;
    private int userID; //外键
    private int planID; //外键
    private LocalDate posterDate;   //帖子发布时间
    private int NumOfLikes;     //帖子的当前点赞数
    private int NumOfCollections;   //帖子的当前收藏数


}