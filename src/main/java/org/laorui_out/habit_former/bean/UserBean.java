package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserBean {
    @TableId(value = "userID", type = IdType.ASSIGN_UUID)
    private int userID;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("userIcon")
    private String userIcon;

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
