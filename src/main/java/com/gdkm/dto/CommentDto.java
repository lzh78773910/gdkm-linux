package com.gdkm.dto;

import com.gdkm.model.Admin;
import com.gdkm.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private Integer cId;
    private Integer parentId;
    private Integer commentator;
    private String content;
    private Date createtime;
    private Date updatetime;
    private Admin admin;
    private User user;

}
