package com.gdkm.controller;

import com.gdkm.model.User;
import com.gdkm.service.ChatlogService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.AppUser;
import com.gdkm.vo.LoginVo;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@Api(value = "登录认证与权限", tags = "登录认证与权限")
public class ByUserLoginController {


    @Autowired
    private ChatlogService chatlogService;
    @Autowired
    private ServletContext servletContext;

    @ApiOperation(value = "登入")
    @PostMapping("/login")
    public ResultVO loginPost(@RequestParam("userName")String userName, @RequestParam("userPass")String userPass){
        //根据前端传递过来的name和passowrd生成shrio的UsernamePasswordToken
        userPass = new Md5Hash(userPass,userName,3).toString();
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

            AppUser appUser=new AppUser();
            BeanUtils.copyProperties(user,appUser);

            if( servletContext.getAttribute("userMap")==null){
                Map<Integer,AppUser> userMap=new HashMap<>();
                userMap.put(user.getUserId(),appUser);
                servletContext.setAttribute("userMap",userMap);
            }else{
                Map<Integer,AppUser> userMap = (Map<Integer,AppUser>)servletContext.getAttribute("userMap");
                userMap.put(user.getUserId(),appUser);
                servletContext.setAttribute("userMap",userMap);
            }


//            if( servletContext.getAttribute("userSet")==null){
//                Set<AppUser> userSet=new HashSet<>();
//                userSet.add(appUser);
//                servletContext.setAttribute("userSet",userSet);
//            }else{
//                Set<AppUser> userSet = (Set<AppUser>)servletContext.getAttribute("userSet");
//                userSet.add(appUser);
//                servletContext.setAttribute("userSet",userSet);
//            }
            return ResultVOUtil.success(loginVo);
        } catch (AuthenticationException e) {
            return ResultVOUtil.error(400,e.getMessage());
        }
    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout/{userId}")
    public ResultVO logout(@PathVariable("userId")Integer userId) {
        Subject subject = SecurityUtils.getSubject();
        if(servletContext.getAttribute("userMap")!=null){
            Map<Integer,AppUser> userMap = (Map<Integer,AppUser>)servletContext.getAttribute("userMap");
                userMap.remove(userId);
        }
        subject.logout();
        return ResultVOUtil.success();
    }

    @ApiOperation(value ="在线用户")
    @GetMapping("/appusers")
    public ResultVO app() {
//        Set<AppUser> userSet = (Set<AppUser>)servletContext.getAttribute("userSet");

//        Set<AppUser> appUsers = chatlogService.userSet(userSet);
        Map<Integer, AppUser> appUsers =null;
        if(servletContext.getAttribute("userMap") !=null) {

            Map<Integer,AppUser> userMap = (Map<Integer,AppUser>)servletContext.getAttribute("userMap");
            appUsers = chatlogService.userMap(userMap);
        }
        return ResultVOUtil.success(appUsers);
    }

}
