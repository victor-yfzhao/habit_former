package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("User")
public class UserBean {
    @TableId(value = "userID", type = IdType.AUTO)
    private Integer userID;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("userIcon")
    private String userIcon = "http://121.40.132.245:8080/habit_former/uploadFile/1720781853083_defaultPicture.jpg";

    @TableField("userCreateDate")
    private Date userCreateDate;

    @TableField("gender")
    private String gender;

    @TableField("address")
    private String address;

    @TableField("userIntro")
    private String userIntro;

    @Override
    public String toString() {
        return "UserBean{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", userCreateDate=" + userCreateDate +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", userIntroduction='" + userIntro + '\'' +
                '}';
    }
}