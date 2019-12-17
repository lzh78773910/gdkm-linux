package com.gdkm.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private  String userName;
    private  String userPass;
    private  String userNickname;
    private  String userNumber;
    private  String userIcon;
    private  String mail;
    private  Date createtime;
}
