package com.gdkm.dto;

import com.gdkm.model.Admin;
import com.gdkm.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionDto {

    private Integer qId;
    private String title;
    private String description;
    private Date createtime;
    private Date updatetime;
    private Integer creator;
    private Admin admin;
    private User user;
}
