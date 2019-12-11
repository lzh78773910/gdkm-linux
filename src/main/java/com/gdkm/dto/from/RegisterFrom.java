package com.gdkm.dto.from;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RegisterFrom {

    private  String userName;
    private  String userPass;
    private  String userNickname;
    private  String userNumber;
    private  String userIcon;
    private  String mail;
    private  String code;
}
