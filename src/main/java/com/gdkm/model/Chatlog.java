package com.gdkm.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class Chatlog {
    @Id
    @GeneratedValue
    private Integer cId;
    /**
     * 发送者
     */
    private Integer user;
    /**
     * 接收者
     */
    private Integer touser;
    /**
     * 内容
     */
    private String cltext;
    /**
     * 是否已读
     * 0未读1已读
     */
    private String clstate;
    /**
     * 时间
     */
    private Date createtime;
}
