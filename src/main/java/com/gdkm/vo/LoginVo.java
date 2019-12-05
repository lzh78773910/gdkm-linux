package com.gdkm.vo;

import com.gdkm.model.User;
import lombok.Data;

@Data
public class LoginVo {
    private User user;
    private String sessionId;
}
