package com.gdkm.dto;

import com.gdkm.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class VideoCommentDto {
    private Integer vcId;
    private Integer parentId;
    private Integer commentator;
    private String vcContent;
    private Date createtime;
    private User user;
}
