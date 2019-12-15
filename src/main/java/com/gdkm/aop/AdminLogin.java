package com.gdkm.aop;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.config.projectUrl;
import com.gdkm.exception.AuthorizeException;
import com.gdkm.model.Admin;
import com.gdkm.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Aspect
@Component
@Slf4j
public class AdminLogin {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository repository;

    @Autowired
    public projectUrl projectUrl;



    @Pointcut("execution(public * com.gdkm.controller.ByAdmin*.*(..))"+
            "&& !execution(public * com.gdkm.controller.ByAdminAdminController.log*(..))"
    )
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin==null){
            log.warn(" session中查不到admin");
            throw new AuthorizeException();
        }
    }
}
