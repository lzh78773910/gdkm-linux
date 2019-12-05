package com.gdkm.service.impl;

import com.gdkm.Repository.HomeLebelRepostory;
import com.gdkm.converter.HomeLabelToHomeLabelDtoConverter;
import com.gdkm.dao.LabelTitleMapper;
import com.gdkm.dto.HomeLabelDto;
import com.gdkm.model.HomeLabel;
import com.gdkm.model.LabelTitle;
import com.gdkm.service.HomeLebelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HomeLebelServiceImpl implements HomeLebelService {

    @Autowired
    private HomeLebelRepostory homeLebelRepostory;

    @Resource
    private LabelTitleMapper labelTitleMapper;

    @Override
    public List<HomeLabelDto> list() {
        List<HomeLabel> homeLabelList = homeLebelRepostory.findAll();
        List<HomeLabelDto> homeLabelDtoList = HomeLabelToHomeLabelDtoConverter.convert(homeLabelList);
        for (HomeLabelDto homeLabelDto:homeLabelDtoList){
            List<LabelTitle> labelTitleList= labelTitleMapper.findByIdAlls(homeLabelDto.getLabelId());
            homeLabelDto.setLabelTitleList(labelTitleList);
        }
        return homeLabelDtoList;
    }
}
