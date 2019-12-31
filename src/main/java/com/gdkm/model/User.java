package com.gdkm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gdkm.utils.Date2LongSerializer;
import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Entity
@DynamicUpdate
@Data
public class User  implements Serializable, AuthCachePrincipal {

    @Id
    @GeneratedValue
    private  Integer userId;
    private  String userName;
    @JsonIgnore
    private  String userPass;
    private  String userNickname;
    private  String userNumber;
    private  String userIcon;
    private  String mail;
    private  Boolean status;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createtime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updatatime;
    private Integer roleId;

    public User() {
    }

    public User(Integer userId, String userName, String userPass) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
