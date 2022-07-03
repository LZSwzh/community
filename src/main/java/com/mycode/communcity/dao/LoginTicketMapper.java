package com.mycode.communcity.dao;

import com.mycode.communcity.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);//插入

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);//以ticket查询

    @Update("update login_ticket set status=#{status} where ticket=#{ticket}")
    int updateStatus(String ticket,int status);//修改凭证状态，用于后续退出功能的实现

}
