package com.gdkm.dto;

import com.gdkm.model.Admin;
import lombok.Data;

import java.util.Date;

@Data
public class ResourceDto {
    private Integer resId;
    private Integer adminId;
    private Integer viewNum;
    private String resTitle;
    private String resContent;
    private String resUrl;
    private Date createtime;
    private Date updatetime;
    private Integer rtId;

    private Admin admin;
}
