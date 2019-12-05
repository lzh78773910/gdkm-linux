package com.gdkm.converter;

import com.gdkm.dto.HomeLabelDto;
import com.gdkm.model.HomeLabel;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class HomeLabelToHomeLabelDtoConverter {

    public static HomeLabelDto convert(HomeLabel homeLabel) {

        HomeLabelDto base = new HomeLabelDto();
        BeanUtils.copyProperties(homeLabel, base);
        return base;
    }

    public static List<HomeLabelDto> convert(List<HomeLabel> baseList) {
        return baseList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
