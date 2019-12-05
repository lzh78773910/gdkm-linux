package com.gdkm.dto;

import com.gdkm.model.LabelTitle;
import lombok.Data;

import java.util.List;

@Data
public class HomeLabelDto {
    private  Integer LabelId;
    private  String homeLabel;
    private  String labelImg;
    private List<LabelTitle> labelTitleList;
}
