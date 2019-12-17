package com.gdkm.dto;

import com.gdkm.model.Admin;
import lombok.Data;

@Data
public class ResourceTypeDto {
    private Integer rtId;
    private String rtTitle;
    private Admin admin;
}
