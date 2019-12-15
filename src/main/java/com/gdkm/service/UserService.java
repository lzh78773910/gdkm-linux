package com.gdkm.service;

import com.gdkm.model.User;
import com.gdkm.vo.from.RegisterVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    //查询单个user
    User findone(Integer userId);

    /**
     *  根据分页查询用户列表
     * @param pageable
     * @return
     */
    Page<User> userPage(Pageable pageable);

    /**
     * 停用或者解封
     * @param userId
     */
    void status(Integer userId);
    /**
     * 添加用户
     */
    User register(RegisterVo registerVo);
    /**
     * 找回密码
     */
    void retrievePassword(String mail,String code,String Pass);
    /**
     * 修改头像
     */
    String userIcon(Integer userId,MultipartFile icon) throws IOException;
    /**
     * 修改用户资料
     */
    User updateUser(User user);
    /**
     * 修改密码
     */
    User changePassword(String oldpass,String pass);
}
