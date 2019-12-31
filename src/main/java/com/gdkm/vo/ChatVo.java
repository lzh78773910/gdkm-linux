package com.gdkm.vo;

import lombok.Data;

@Data
public class ChatVo {
    /**
     * 发送者
     */
    private Integer userId;
    /**
     * id
     */
    private Integer cId;
    /**
     * 头像
     */
    private String userIocn;
    /**
     * 内容
     */
    private String cltext;

    /**
     * 0是未读
     */
    private String clstate;
    /**
     * 0是自己  1是对方
     */
    private String tpye;
}
