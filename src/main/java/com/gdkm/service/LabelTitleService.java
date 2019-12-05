package com.gdkm.service;

import com.gdkm.model.LabelTitle;

import java.util.List;

public interface LabelTitleService {
    /**
     * 查询标签list下的所有内容
     */
    List<LabelTitle> list();
}
