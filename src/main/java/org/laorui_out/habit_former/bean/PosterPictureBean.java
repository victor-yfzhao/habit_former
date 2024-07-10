package org.laorui_out.habit_former.bean;

import lombok.Data;

@Data
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
