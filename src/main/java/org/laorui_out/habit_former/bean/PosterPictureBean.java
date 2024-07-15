package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Posterpicture")
public class PosterPictureBean {

    private int posterID;
    private String posterPicture;

    @Override
    public String toString() {
        return "PosterPictureBean{" +
                "posterID=" + posterID +
                ", singlePicture='" + posterPicture + '\'' +
                '}';
    }

}
