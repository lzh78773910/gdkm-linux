package com.gdkm.dao;

import com.gdkm.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findone() {
        User user = userMapper.findone(1);
        System.out.println(user);
    }

    @Test
    public void save() {
        User user=new User();
        user.setUserName("00");
        user.setUserPass("0.0");
        user.setUserNumber("18602001033");
        user.setStatus(true);
        user.setUserNickname("666");

        int save = userMapper.save(user);
    }
}