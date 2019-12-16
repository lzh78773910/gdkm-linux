package com.gdkm.dto;

import com.gdkm.model.Admin;
import lombok.Data;

import java.util.Date;

@Data
public class VideoDto {
    private Integer videoId;

    private Integer adminId;
    private  String  videoText;
    private  String videoTitle;
    private  String videoUrl;
    private Integer viewNum;
    private String coursewareUrl;
    private String videoImg;
    private Date createtime;
    private Date updatatime;

    private Admin admin;

}
