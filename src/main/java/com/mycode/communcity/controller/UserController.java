package com.mycode.communcity.controller;

import com.mycode.communcity.Annotation.LoginRequired;
import com.mycode.communcity.entity.LoginTicket;
import com.mycode.communcity.entity.User;
import com.mycode.communcity.service.UserService;
import com.mycode.communcity.util.CommunityUtil;
import com.mycode.communcity.util.CookieUtil;
import com.mycode.communcity.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${community.path.upload}")
    private String uploadPath;//上传路径
    @Value("${community.path.domain}")
    private String domain;//域名
    @Value("${server.servlet.context-path}")
    private String contextPath;//项目路径
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;//引入ThreadLocal工具类


    /**
     * 上传头像
     */
    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headImage, Model model){
        //判断图片是否为空
        if (headImage == null){
            model.addAttribute("error","您还未选择图片");
            return "/site/setting";
        }
        //暂存文件后缀
        String fileName = headImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件格式不正确");
            return "/site/setting";
        }
        //随机文件名生成
        fileName = CommunityUtil.generateUUID()+suffix;
        //确定文件路径(本地路径)---------d:/......
        File dest = new File(uploadPath+"/"+fileName);
        try {
            //存储文件
            headImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败:"+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常:"+e);
        }
        //更新头像路径(web访问路径)
        User user = hostHolder.getUser();
        //形如:http://localhost:8080/community/user/header/xxx.png
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(),headerUrl);
        return "redirect:/index";
    }

    /**
     * 获取头像:由于向网页响应图片，所以通过流手动输出
     */
    @RequestMapping(path = "/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //服务器存放的路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/"+suffix);
        //图片是二进制数据，使用字节流读写
        FileInputStream fis = null;
        try {
            //输出流
            ServletOutputStream os = response.getOutputStream();
            //输入流用于读取文件
            fis = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];//缓冲区
            int b = 0;//浮标
            while((b= fis.read(buffer))!=-1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败"+e.getMessage());
        }finally {
            //输出流由mvc管理，而输入流由自己创建需要手动关闭
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 修改密码:
     */
    @RequestMapping(path = "/updatePwd",method = RequestMethod.POST)
    public String updatePwd(HttpServletRequest request,Model model,
            String oldPwd,String newPwd,String newPwdAgain){
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user, oldPwd, newPwd, newPwdAgain);
        if (map.isEmpty()){
            model.addAttribute("msg","您的账号密码修改成功！");//中转页面信息
            model.addAttribute("target","/index");//中专页面跳转连接
            return "/site/operate-result";//跳转到中转页面
        }
        model.addAttribute("oldMsg",map.get("oldMsg"));
        model.addAttribute("newMsg",map.get("newMsg"));
        model.addAttribute("newAginMsg",map.get("newAginMsg"));
        return "/site/setting";
    }
    /**
     * 跳转账号设置页面
     */
    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

}
