package com.bingo.web.springbootdemo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_XHM_CONSOLE_USER")
public class ConsoleUser {
    @TableId("ID")
    private String id;
    @TableField("PROJECTCODE")
    private String projectCode;
    @TableField("USERNAME")
    private String username;
    @TableField("PASSWORD")
    private String password;
    @TableField("NICKNAME")
    private String nickname;
    @TableField("EMAIL")
    private String email;
    @TableField("CREATE_TIME")
    private Date createTime;
    @TableField("LAST_LOGIN_TIME")
    private Date lastLoginTime;
    @TableField("STATUS")
    private String status;
}
