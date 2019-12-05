package com.gdkm.service.impl;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.config.projectUrl;
import com.gdkm.model.Admin;
import com.gdkm.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AdminServiceImplTest {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private AdminService service;
    @Autowired
    private projectUrl projectUrl;


    @Test
    public void getAdmin() {
        Admin one = repository.findOne(1);
        System.out.println(one.toString());
    }

    @Test
    public void updataAdmin() {
    }

    @Test
    public void longin() {
        Admin admin = service.longin("test", "test");
        System.out.println(admin.toString());
    }

    @Test
    public void createAdmin() {
        Admin admin =new Admin();
        admin.setAdminName("test");
        admin.setAdminPass("test");
        admin.setAdminNickname("测试");
        admin.setStatus(true);
        Admin save = service.createAdmin(admin);
        System.out.println(save.toString());
    }
}