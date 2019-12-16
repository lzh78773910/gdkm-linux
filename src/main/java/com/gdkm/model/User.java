package com.gdkm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gdkm.utils.Date2LongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
@DynamicUpdate
@Data
public class User {

    @Id
    @GeneratedValue
    private  Integer userId;
    private  String userName;
    private  String userPass;
    private  String userNickname;
    private  String userNumber;
    private  String userIcon;
    private  Boolean status;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createtime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updatatime;

    public User() {
    }

    public User(Integer userId, String userName, String userPass) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }
}
