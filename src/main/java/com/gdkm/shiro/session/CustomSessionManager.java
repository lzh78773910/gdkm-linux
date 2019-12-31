package com.gdkm.shiro.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.Serializable;

public class CustomSessionManager extends DefaultWebSessionManager {
    /**
     * 设置会话时间
     */
    public CustomSessionManager() {
        super();
        setGlobalSessionTimeout(DEFAULT_GLOBAL_SESSION_TIMEOUT * 48);
    }

    /**
     * 请求头Authorization：sessionid
     * 指定sessionid的获取方法
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //获取请求头信息
        String id= WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(id)) {
            //为空则创建新的sessionid
            return super.getSessionId(request,response);
        }else {
            id=id.replaceAll("beared","");
            //在哪里获取
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            //id是什么
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            //是否要验证
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
    }

}
