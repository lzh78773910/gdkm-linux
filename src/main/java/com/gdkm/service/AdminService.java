package com.gdkm.service;

import com.gdkm.model.Admin;

public interface AdminService {

    /**
     * 查找信息
     */
    Admin getAdmin(Integer adminId);

    /**
     * 修改密码信息
     * @param admin
     */
    void updataAdmin(Admin admin);

    /**
     * 管理员登录校验
     * @param adminName
     * @param adminPass
     * @return
     */
    Admin longin(String adminName,String adminPass);

    /**
     * 创建管理员
     * @param admin
     */
    Admin createAdmin(Admin admin);
}
