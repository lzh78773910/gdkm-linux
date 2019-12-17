package com.gdkm.controller;

import com.gdkm.dto.UserDto;
import com.gdkm.enums.ResultEnum;
import com.gdkm.exception.LinuxException;
import com.gdkm.model.User;
import com.gdkm.service.UserService;
import com.gdkm.utils.*;
import com.gdkm.vo.ResultVO;
import com.gdkm.vo.from.RegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户控制层
 */

@Api(value = "用户表操作", tags = "用户表操作")
@RestController
public class UserController {
    @Autowired
    private UCloudProvider uCloudProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String myMail;

    @ApiOperation(value = "发送邮箱验证码")
    @PostMapping("/user/mail")
    public ResultVO sendMail(String mail, HttpServletResponse response) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(mail);//收件人邮箱
        simpleMailMessage.setFrom(myMail);//发送者邮箱
        simpleMailMessage.setSubject("请查收验证码");
        String number = KeyUtil.getNumber();
        simpleMailMessage.setText("你的验证码是:"+ number+",3分钟后会过期");
        //保存验证码session
        //找到文件的后缀
        int lastIndexOf = mail.lastIndexOf("@");
        String mailhouzhui = mail.substring(0,lastIndexOf);
        CookieUtil.set(response, mailhouzhui, number, 60*3);
        // 发送邮件
        mailSender.send(simpleMailMessage);
        return ResultVOUtil.success("发送邮箱成功");
    }

    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/user")
    public ResultVO  register(RegisterVo registerVo,  HttpServletRequest request) throws IOException {
        //获取验证码
        String mail = registerVo.getMail();
        int lastIndexOf = mail.lastIndexOf("@");
        String mailhouzhui = mail.substring(0,lastIndexOf);
        Cookie cookie = CookieUtil.get(request, mailhouzhui);
        String number = cookie.getValue();
        //判空
        if(StringUtil.isEmpty(number)){
            return ResultVOUtil.error(ResultEnum.CODE_ERROR.getCode(),ResultEnum.CODE_ERROR.getMessage());
        }
        //核对
        if(number.equals(registerVo.getCode())){
            try {
                userService.register(registerVo);
            }catch (Exception e){
                return ResultVOUtil.error(e.getMessage());
            }
            return ResultVOUtil.success("注册成功");
        }
        return ResultVOUtil.error(ResultEnum.CODE_ERROR.getCode(),ResultEnum.CODE_ERROR.getMessage());
    }

    /**
     * 找回密码
     */
    @ApiOperation(value = "找回密码")
    @PutMapping("/user/mail")
    public ResultVO retrievePassword(String mail,String code,String pass){
        try {
            userService.retrievePassword(mail,code,pass);
        }catch (Exception e){
            return ResultVOUtil.error(e.getMessage());
        }
        return ResultVOUtil.success("密码已修改");
    }

    /**
     * 获取信息
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/user/{userId}")
    public ResultVO select(@PathVariable(value = "userId")Integer userId){
        User user = userService.findone(userId);
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return ResultVOUtil.success(userDto);
    }

    /**
     * 修改头像
     */
    @ApiOperation(value = "修改头像")
    @PostMapping("/byuser/user/icon")
    public ResultVO icon(MultipartFile icon) throws IOException {
        String userIcon = userService.userIcon(icon);
        return ResultVOUtil.success(userIcon);
    }
    /**
     * 修改密码
     */
    @ApiOperation(value = "修改密码")
    @PutMapping("/byuser/user/pass")
    public ResultVO changePassword(String oldpass,String pass)  {
        try { userService.changePassword(oldpass,pass); }catch (Exception e){ return ResultVOUtil.error(e.getMessage()); }
        return ResultVOUtil.success();
    }
    /**
     * 修改资料 （学号,昵称）
     */
    @ApiOperation(value = "修改资料 （学号,昵称）")
    @PutMapping("/byuser/user")
    public ResultVO updateUser(String userNumber,String userNickname)  {
        User user=new User();
        user.setUserNumber(userNumber);
        user.setUserNickname(userNickname);
        User result = userService.updateUser(user);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "测试上传")
    @PostMapping("/user/icon2")
    public ResultVO icon1(MultipartFile file) throws IOException {
        String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename(),null);
        return ResultVOUtil.success(fileName);
    }
    @ApiOperation(value = "测试下载")
    @GetMapping("/user/icon3/xiazai")
    public ResultVO icon3() {
        String url="http://gdkmlzh.cn-gd.ufileos.com/a%2Fb%2Fc.jpg?UCloudPublicKey=7XUUrIZu_COBbktItgfRT0tYkbMP_GSP-OWjXsTe&Signature=uT%2FB5hLW7Ax9jCEG32kdzWkVREg%3D&Expires=1576472148";
         uCloudProvider.getStream(url);
        return ResultVOUtil.success();
    }

}
