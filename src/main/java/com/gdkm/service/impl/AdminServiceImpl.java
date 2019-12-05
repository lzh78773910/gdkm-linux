package com.gdkm.service.impl;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.enums.ResultEnum;
import com.gdkm.exception.LinuxException;
import com.gdkm.model.Admin;
import com.gdkm.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository repository;

    @Override
    public Admin getAdmin(Integer adminId) {
        return repository.findOne(adminId);
    }

    @Override
    public void updataAdmin(Admin admin) {
        Admin one = repository.findOne(admin.getAdminId());
        one.setAdminPass(admin.getAdminPass());
        repository.save(one);
    }

    @Override
    public Admin longin(String adminName, String adminPass) {
        Admin admin = repository.findByAdminName(adminName);
        if (admin==null){
            log.error("[管理员登录] 该管理员不存在，adminName={},"+adminName);
            return null;
        }else {
            if (admin.getAdminPass().equals(adminPass)) {
                return admin;
            } else {
                log.error("[管理员登录] 管理员密码校验错误");
                return null;
            }
        }
    }

    @Override
    public Admin createAdmin(Admin admin) {
        Admin byAdminName = repository.findByAdminName(admin.getAdminName());
        if(byAdminName==null){
            admin.setStatus(false);
            Admin save = repository.save(admin);
            return save;
        }else{
            return null;
        }

    }
}
