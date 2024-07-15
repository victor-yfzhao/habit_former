package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPersonalPageBean {
    private Integer userID;
    private String username;
    private String userIcon;
    private String gender;
    private String address;
    private String userIntro;
    private List<Object> personalPosterList;


    @Override
    public String toString() {
        return "UserPersonalPageBean{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", userIntroduction='" + userIntro + '\'' +
                ", personalPosterList=" + personalPosterList +
                '}';
    }
}
