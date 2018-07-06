package com.bingo.web.springbootdemo.mapper;

import com.bingo.web.springbootdemo.entity.ConsoleUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ConsoleUserMapper {

    @Select("select id, username, password from t_xhm_console_user where username = #{username} and projectcode = #{projectcode}")
    @Results({
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "lastLoginTime", column = "last_login_time")
    })
    ConsoleUser findByUsername(@Param("username") String username, @Param("projectcode") String projectcode);

    @Update("update t_xhm_console_user t set t.password = #{newPwd} where t.id = #{id}")
    void updatePassword(@Param("id") String id, @Param("newPwd") String newPwd);

}
