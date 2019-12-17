package com.gdkm.enums;


import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultEnum {

    ADMIN_LONGIN_FALSE(00,"管理员不存在"),
    ADMIN_LONGIN_PASS_FALSE(01,"管理员密码错误"),
    ADMIN_LONGIN_NAMEORPASS_FALSE(02,"账号或者密码错误"),
    ADMIN_ADD_FALSE(3,"管理员已存在"),
    AUTH(401,"认证不通过，请重新认证"),
    PERMISSION(402,"权限不足，无法访问"),
    CODE_ERROR(51,"验证码过期或者错误"),
    PASS_VERIFY_ERREO(3,"密码核对错误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
