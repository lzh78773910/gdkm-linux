package com.gdkm.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class AdminLoginToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 2020457391511655213L;

    public AdminLoginToken() {}

    public AdminLoginToken(final String name, final String password) {
        super(name, password);
    }

}