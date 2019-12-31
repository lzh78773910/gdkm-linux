package com.gdkm.vo;

import lombok.Data;

@Data
public class AppUser {

    private  Integer userId;
    private  String userNickname;
    private  String userIcon;
    //false则有信息未读
    private  Boolean xinxi=false;
}
