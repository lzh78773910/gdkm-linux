package com.gdkm.exception;

import com.gdkm.config.projectUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AdminExceptionHandler {
    @Autowired
    public projectUrl projectUrl;
    //拦截登录异常
    @ExceptionHandler(value =  AuthorizeException.class)
    public void handlerAuthorizeException(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //跳转登录页面
       response.sendRedirect(request.getContextPath()+"/byadmin/admin/login");
    }
}


