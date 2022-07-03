package com.mycode.communcity.service;

import com.mycode.communcity.dao.LoginTicketMapper;
import com.mycode.communcity.dao.UserMapper;
import com.mycode.communcity.entity.LoginTicket;
import com.mycode.communcity.entity.User;
import com.mycode.communcity.util.CommunityConstant;
import com.mycode.communcity.util.CommunityUtil;
import com.mycode.communcity.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;//注入邮件客户端，用于发送邮件
    @Autowired
    private TemplateEngine templateEngine;//注入模板引擎，用于发送动态邮件
    @Value("${community.path.domain}")
    private String domain;//注入域名
    @Value("${server.servlet.context-path}")
    private String contextPath;//注入项目路径

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    /**
     * 注册方法
     */
    public Map<String,Object> register(User user){
        HashMap<String, Object> map = new HashMap<>();
        if(user==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空");
            return map;
        }
        //验证账号是否已经存在
        User temp = userMapper.selectByName(user.getUsername());
        if(temp!=null){
            map.put("usernameMsg","该账号已经存在");
            return map;
        }
        //验证邮箱
        temp = userMapper.selectByEmail(user.getEmail());
        if (temp!=null){
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }
        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));//随机字符串生成
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);//普通用户
        user.setStatus(0);//未激活
        user.setActivationCode(CommunityUtil.generateUUID());//激活码
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));//设置随机头像
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //发送邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());//邮箱地址
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();//拼接请求地址
        context.setVariable("url",url);//设置请求地址
        String content = templateEngine.process("/mail/activation",context);//thymeleaf渲染邮件内容
        mailClient.sendMail(user.getEmail(),"激活账号",content);//发送邮件

        return map;
    }
    /**
     * 激活业务
     *      成功0、失败2、重复激活1,定义在接口CommunityConstant中,让本类实现该接口
     */
    public int activation(int userId,String code){
        User user = userMapper.selectById(userId);
        if (user.getStatus()==1){
            return ACTIVATION_REPEAT;
        }else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else{
            return ACTIVATION_FAILURE;
        }
    }

    /**
     * 登陆业务
     */
    public Map<String,Object> login(String username,String password,int expiredSecond){
        HashMap<String, Object> map = new HashMap<>();
        //空值处理
        if (StringUtils.isBlank(username)){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        User user = userMapper.selectByName(username);
        if(user == null){
            map.put("usernameMsg","该账号不存在");
            return map;
        }
        if (user.getStatus() == 0){
            map.put("usernameMsg","该账号未激活");
            return map;
        }
        //验证密码
        password = CommunityUtil.md5(password+user.getSalt());
        if (user.getPassword().equals(password)){
            map.put("passwordMsg","密码不正确");
            return map;
        }
        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expiredSecond*1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        //客户端记录登陆凭证
        map.put("ticket",loginTicket.getTicket());
        return map;
    }
    /**
     * 退出
     */
    public void logout(String ticket){
        loginTicketMapper.updateStatus(ticket,1);
    }
    /**
     * 和POST层中的查询配合，将查出来的user_id替换为name
     */
    public User findUserByid(int id){
        return userMapper.selectById(id);
    }
}
