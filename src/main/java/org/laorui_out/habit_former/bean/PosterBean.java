package com.example.postarrangement.bean;

import lombok.Data;

import java.util.List;

@Data   //采用lombok的@Data注解后不用再get和set
public class PosterBean {

    private int posterID;
    private String posterHeadline;
    private List<String> posterPicture;
    private String posterDetail;
    //图像的类型

    @Override
    public String toString() {
        return "PosterBean{" +
                "posterID=" + posterID +
                ", posterHeadline='" + posterHeadline + '\'' +
                ", posterPicture=" + posterPicture +
                ", posterDetail='" + posterDetail + '\'' +
                '}';
    }
}
