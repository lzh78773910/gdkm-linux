package com.gdkm.service.impl;

import com.gdkm.model.User;
import com.gdkm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void findone() {
        User findone = userService.findone(1);
        System.out.println(findone.toString()+"......................................................");
    }
    @Test
    public void pageUser(){
        PageRequest request=new PageRequest(0,3);
        Page<User> userPage = userService.userPage(request);
        Assert.assertNotEquals(0,userPage.getTotalElements());
    }

    @Test
    public void status(){
        userService.status(1);
    }
}