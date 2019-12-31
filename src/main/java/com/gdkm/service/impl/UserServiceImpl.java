package com.gdkm.service.impl;

import com.gdkm.Repository.UserRepository;
import com.gdkm.config.projectUrl;
import com.gdkm.dao.UserMapper;
import com.gdkm.enums.ResultEnum;
import com.gdkm.exception.LinuxException;
import com.gdkm.model.User;
import com.gdkm.service.UserService;
import com.gdkm.utils.CookieUtil;
import com.gdkm.utils.UCloudProvider;
import com.gdkm.vo.from.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UCloudProvider uCloudProvider;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserRepository repository;

    @Autowired
    public projectUrl projectUrl;

    @Override
    public User findone(Integer userId) {
        return userMapper.findone(userId);
    }

    public void status(Integer userId){
        User findone = repository.findOne(userId);
        if (findone.getStatus()==true){
            findone.setStatus(false);
        }else{
            findone.setStatus(true);
        }
        repository.save(findone);
    }

    @Override
    public User register(RegisterVo registerVo) {
        User byuserName = repository.findByuserName(registerVo.getUserName());
        if(byuserName!=null){
            throw new LinuxException("用户名已注册");
        }
        User byMail = repository.findByMail(registerVo.getMail());
        if(byMail!=null){
            throw new LinuxException("邮箱已注册");
        }
        User user=new User();
        BeanUtils.copyProperties(registerVo,user);
        user.setRoleId(1);
        user.setUserIcon(projectUrl.getImg()+"icon.jpg");
        String userPass = new Md5Hash(user.getUserPass(),user.getUserName(),3).toString();
        user.setUserPass(userPass);
        User save = repository.save(user);
        return save;
    }

    @Override
    public void retrievePassword(String mail, String code, String Pass) {
        //先根据邮箱获取用户
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        int lastIndexOf = mail.lastIndexOf("@");
        String mailhouzhui = mail.substring(0,lastIndexOf);
        Cookie cookie = CookieUtil.get(request, mailhouzhui);
        String number = cookie.getValue();
        if(!number.equals(code)){
            log.error("[用户操作]找回密码,错误状态码{},错误信息{}",ResultEnum.CODE_ERROR.getCode(),ResultEnum.CODE_ERROR.getMessage());
            throw new LinuxException(ResultEnum.CODE_ERROR);
        }
        User byMail = repository.findByMail(mail);
        Pass = new Md5Hash(Pass,byMail.getUserName(),3).toString();
        byMail.setUserPass(Pass);
        repository.save(byMail);
    }

    @Override
    public String userIcon(MultipartFile icon) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        User user= (User)subject.getPrincipal();
        String fileName = uCloudProvider.upload(icon.getInputStream(), icon.getContentType(), icon.getOriginalFilename(), projectUrl
        .getImgUcloud());
        user.setUserIcon(fileName);
        User save = repository.save(user);
        return save.getUserIcon();
    }

    @Override
    public User updateUser(User user) {
        Subject subject = SecurityUtils.getSubject();
        User one= (User)subject.getPrincipal();
        one.setUserNumber(user.getUserNumber());
        one.setUserNickname(user.getUserNickname());
        User save = repository.save(one);
        return save;
    }

    @Override
    public User changePassword(String oldpass,String pass) {
        Subject subject = SecurityUtils.getSubject();
        User one= (User)subject.getPrincipal();
        oldpass=new Md5Hash(oldpass,one.getUserName(),3).toString();
        if (!one.getUserPass().equals(oldpass)){
            log.error("【用户操作】修改密码，密码不核对，修改失败");
            throw new LinuxException(ResultEnum.PASS_VERIFY_ERREO);
        }
        pass=new Md5Hash(pass,one.getUserName(),3).toString();
        one.setUserPass(pass);
        User save = repository.save(one);
        return save;
    }

    public Page<User> userPage(Pageable pageable){
        Page<User> userPage = repository.findAll(pageable);
        List<User> userList = userPage.getContent();
        return new PageImpl<>(userList,pageable,userPage.getTotalPages());
    }

}
