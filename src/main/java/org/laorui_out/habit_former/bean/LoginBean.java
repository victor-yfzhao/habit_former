package org.laorui_out.habit_former.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_log")
public class LoginBean {
    @TableId(value = "LogID")
    private Integer LogID;

    @TableField("userID")
    private int userID;

    @TableField("Log_Content")
    private String Log_Content;

    @TableField("IP_Address")
    private String IP_Address;

    @TableField("OS")
    private String OS;

    @TableField("IE")
    private String IE;

    @TableField("CreateDate")
    private Date CreateDate;

    @TableField("Remark")
    private String Remark;
}
