package com.gdkm.controller;

import com.gdkm.model.User;
import com.gdkm.service.UserService;
import com.gdkm.utils.KeyUtil;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.utils.StringUtil;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户控制层
 */

@Api(value = "用户表操作", tags = "用户表操作")
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSenderImpl mailSender;
    @Value("${spring.mail.username}")
    private String myMail;

    @ApiOperation(value = "发送邮箱验证码")
    @PostMapping("/mail")
    public void sendMail(String mail, HttpSession session) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(mail);//收件人邮箱
        simpleMailMessage.setFrom(myMail);//发送者邮箱
        simpleMailMessage.setSubject("请查收验证码");
        String number = KeyUtil.getNumber();
        simpleMailMessage.setText("你的验证码是:"+ number);
        //保存验证码session
        session.setAttribute(mail,number);
        session.setMaxInactiveInterval(60);
        // 发送邮件
        mailSender.send(simpleMailMessage);
        System.err.println("邮件已发送");
    }

    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/reg")
    public ResultVO  register(User user,String code){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        HttpSession session = request.getSession();
        String number = (String)session.getAttribute(user.getMail());
        if(StringUtil.isEmpty(number)){
            return ResultVOUtil.error(50,"验证码错误");
        }
        if(number.equals(code)){
            userService.add(user);
        }
        return ResultVOUtil.success();
    }

    @ApiOperation(value = "获取验证码")
    @PostMapping("/huoqu")
    public ResultVO  huoqu(String mail,String code, HttpSession session){
        String number = (String)session.getAttribute(mail);
        if(StringUtil.isNotEmpty(number)){
            return ResultVOUtil.error(50,"验证码错误或过期");
        }
        if(number.equals(code)){
            return ResultVOUtil.success("验证成功"+number);
        }
        return ResultVOUtil.success(number);
    }

    /**
     * 找回密码
     */

    /**
     * 修改信息
     */

    /**
     * 获取信息
     */


}
