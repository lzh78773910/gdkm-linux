package com.gdkm.service;

import com.gdkm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
