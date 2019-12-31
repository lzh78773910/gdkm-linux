package com.gdkm.service.impl;

import com.gdkm.utils.IpUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Component
public class ServletRequestListenerImpl implements ServletRequestListener {
    private Set<String> ipSet = new HashSet<>();

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        ipSet.add(IpUtil.getIpAddr(request));
        sre.getServletContext().setAttribute("ipSet", ipSet);
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void chear(){
        ipSet=new HashSet<>();
    }
}