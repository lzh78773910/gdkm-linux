package com.gdkm.dao;

import com.gdkm.model.LabelTitle;

import java.util.List;

public interface LabelTitleMapper {
    List<LabelTitle> findByIdAlls(Integer labelId);
}
