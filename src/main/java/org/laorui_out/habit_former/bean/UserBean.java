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
@TableName("user")
public class UserBean {
    @TableId(value = "userID", type = IdType.ASSIGN_ID)
    private Integer userID;

    @TableField("username")
    private String username;

    @TableField("password")
    @JsonIgnore
    private String password;

    @TableField("userIcon")
    private String userIcon;

    @TableField("userCreateDate")
    @JsonIgnore
    private Date userCreateDate;

    @Override
    public String toString() {
        return "UserBean{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userIcon='" + userIcon + '\'' +
                '}';
    }
}
