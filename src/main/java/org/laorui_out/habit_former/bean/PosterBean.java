package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Data
@TableName("Poster")
public class PosterBean {
    @TableId(value = "posterID", type = IdType.AUTO)
    private int posterID;
    @TableField("posterHeadline")
    private String posterHeadline;

    @TableField(exist = false)
    private List<String> posterPicture;

    @TableField("posterDetail")
    private String posterDetail;
    @TableField("userID")
    private int userID; //外键
    @TableField("planID")
    private int planID; //外键
    @TableField("posterDate")
    private LocalDate posterDate;   //帖子发布时间

    @TableField(exist = false)
    private int numOfLikes;     //帖子的当前点赞数

    @TableField(exist = false)
    private int numOfCollections;   //帖子的当前收藏数


}