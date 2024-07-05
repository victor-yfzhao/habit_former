package org.laorui_out.habit_former.bean;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class PosterBean {

    private int posterID;
    private String posterHeadline;
    private String[] posterPicture;
    private String posterDetail;
    //图像的类型


    @Override
    public String toString() {
        return "PosterBean{" +
                "posterID=" + posterID +
                ", posterHeadline='" + posterHeadline + '\'' +
                ", posterPicture=" + Arrays.toString(posterPicture) +
                ", posterDetail='" + posterDetail + '\'' +
                '}';
    }
}