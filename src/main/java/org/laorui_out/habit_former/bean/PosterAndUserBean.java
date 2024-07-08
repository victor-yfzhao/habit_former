package org.laorui_out.habit_former.bean;

import lombok.Data;

import java.util.Arrays;

@Data
public class PosterAndUserBean {

        private int userID;
        private String username;
        private String userIcon;
        private int posterID;
        private String posterHeadline;
        private String[] posterPicture;
        private String posterDetail;

        @Override
        public String toString() {
                return "PosterAndUserBean{" +
                        "userID=" + userID +
                        ", username='" + username + '\'' +
                        ", userIcon='" + userIcon + '\'' +
                        ", posterID=" + posterID +
                        ", posterHeadline='" + posterHeadline + '\'' +
                        ", posterPicture=" + Arrays.toString(posterPicture) +
                        ", posterDetail='" + posterDetail + '\'' +
                        '}';
        }
}
