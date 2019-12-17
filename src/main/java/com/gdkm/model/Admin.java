package com.gdkm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.crazycake.shiro.AuthCachePrincipal;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Entity
@DynamicUpdate
@Getter
@Setter
public class Admin  implements Serializable, AuthCachePrincipal {

    @Id
    @GeneratedValue
    private Integer adminId;
    private String adminName;
    @JsonIgnore
    private String adminPass;
    private String adminNickname;
    private String adminIcon;
    private  Boolean status;
    private Date createtime;
    private Date updatatime;

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
