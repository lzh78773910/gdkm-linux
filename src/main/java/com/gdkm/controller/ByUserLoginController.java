package com.gdkm.controller;

import com.gdkm.model.User;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.LoginVo;
import com.gdkm.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("")
public class ByUserLoginController {

    @GetMapping(value = "/byuser/user")
    public User user()
    {
        Subject subject = SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        return user;
    }


    @PostMapping("/login")
    public ResultVO loginPost(@RequestParam("userName")String userName, @RequestParam("userPass")String userPass){
        //根据前端传递过来的name和passowrd生成shrio的UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(userName, userPass);
        //写shiro的认证逻辑
        Subject subject = SecurityUtils.getSubject();
        //因为可能出现异常,所以直接try catch
        try {
            //调用login方法,传入token
            subject.login(token);
            String sid = (String) subject.getSession().getId();
            //如果登录没有出现异常的话,就可以通过getPrincinpal()获取登录用户
            User user= (User)subject.getPrincipal();
            //获取sessionId
            String sessionId = (String) subject.getSession().getId();
            //成功返回id和对象
            LoginVo loginVo=new LoginVo();
            loginVo.setUser(user);
            loginVo.setSessionId(sessionId);
            return ResultVOUtil.success(loginVo);
        } catch (AuthenticationException e) {
            return ResultVOUtil.error(400,e.getMessage());
        }
    }
}
