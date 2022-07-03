package com.mycode.communcity.mapper;

import com.mycode.communcity.dao.LoginTicketMapper;
import com.mycode.communcity.entity.LoginTicket;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class lgginMapperTest {
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Test
    public void testInsert(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000*60*10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }
    @Test
    public void testSelect(){
        String ticket="abc";
        LoginTicket res = loginTicketMapper.selectByTicket(ticket);
        System.out.println(res);
    }
    @Test
    public void testUpdate(){
        loginTicketMapper.updateStatus("abc",0);
    }
}
