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
public class Admin {

    @Id
    @GeneratedValue
    private Integer adminId;
    private String adminName;
    private String adminPass;
    private String adminNickname;
    private String adminIcon;
    private  Boolean status;
    private Date createtime;
    private Date updatatime;

}
