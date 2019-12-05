package com.gdkm.service;

import com.gdkm.dto.HomeLabelDto;

import java.util.List;

public interface HomeLebelService {
    /**
     * 查询所有标签list
     */
    List<HomeLabelDto> list();
}
