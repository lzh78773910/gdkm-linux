package com.gdkm.service.impl;

import com.gdkm.Repository.HomeLebelRepostory;
import com.gdkm.Repository.LabelTitleRepostory;
import com.gdkm.converter.HomeLabelToHomeLabelDtoConverter;
import com.gdkm.dto.HomeLabelDto;
import com.gdkm.model.HomeLabel;
import com.gdkm.model.LabelTitle;
import com.gdkm.service.LabelTitleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class LabelTitleServiceImpl implements LabelTitleService {
    @Autowired
    private HomeLebelRepostory homeLebelRepostory;
    @Autowired
    private LabelTitleRepostory labelTitleRepostory;

//    @Override
//    public List<LabelTitle> list() {
//       List<HomeLabel> homeLabelList = homeLebelRepostory.findAll();
//       //取出HomeLabel的id集合
//        List<Integer> homeLabelListId=new ArrayList<>();
//        for (HomeLabel homeLabel: homeLabelList){
//            homeLabelListId.add(homeLabel.getLabelId());
//        }
//
//        List<LabelTitle> labelTitleList  = labelTitleRepostory.findAll();
//        List<Integer> labelTitleListId=new ArrayList<>();
//        for (LabelTitle labelTitle: labelTitleList){
//            homeLabelListId.add(labelTitle.getLabelId());
//        }
//
//        List<HomeLabelDto> homeLabelDtoList = HomeLabelToHomeLabelDtoConverter.convert(homeLabelList);
//        HomeLabelDto homeLabelDto=new HomeLabelDto();
//            for (Integer labelId : homeLabelListId){
//                if(labelTitleListId.contains(labelId)){
//                    HomeLabel homeLabel = homeLebelRepostory.findOne(labelId);
//
//
//                }
//            }
//
//        return null;
//    }

    @Override
    public List<LabelTitle> list() {
        return null;
    }
}
