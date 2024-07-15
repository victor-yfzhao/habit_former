package org.laorui_out.habit_former.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class PosterAndUserBean {

        private int userID;
        private String username;
        private String userIcon;
        private int posterID;
        private String posterHeadline;
        private List<String> posterPicture;
        private String posterDetail;
        private LocalDate posterDate;
        private int planID;
        private String planName;
        private String planType;
        private int numOfLikes;
        private int numOfCollections;

}
