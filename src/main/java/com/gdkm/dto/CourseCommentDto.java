package com.gdkm.dto;

import com.gdkm.model.Admin;
import com.gdkm.model.User;
import lombok.Data;

import java.util.Date;
@Data
public class CourseCommentDto {

    private Integer ccId;
    private Integer userId;
    private String userContent;
    private Integer adminId;
    private String adminContent;
    private Date createtime;
    private Date updatatime;

    private Admin admin;
    private User user;
}
