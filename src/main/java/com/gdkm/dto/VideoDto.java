package com.gdkm.dto;

import com.gdkm.model.Admin;
import com.gdkm.model.VideoItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

    private List<VideoItem> videoItem;

    private Admin admin;

}
