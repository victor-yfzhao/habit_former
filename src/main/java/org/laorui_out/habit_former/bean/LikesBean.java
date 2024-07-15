package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Likes")
public class LikesBean {
    @TableField("userID")
    private int userID;

    @TableField("posterID")
    private int posterID;
}