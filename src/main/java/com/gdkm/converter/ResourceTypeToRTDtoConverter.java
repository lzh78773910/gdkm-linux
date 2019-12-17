package com.gdkm.converter;

import com.gdkm.dto.ResourceTypeDto;
import com.gdkm.model.ResourceType;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceTypeToRTDtoConverter {

    public static ResourceTypeDto convert(ResourceType resourceType) {
        ResourceTypeDto resourceTypeDto = new ResourceTypeDto();
        BeanUtils.copyProperties(resourceType, resourceTypeDto);
        return resourceTypeDto;
    }

    public static List<ResourceTypeDto> convert(List<ResourceType> resourceTypeList) {
        return resourceTypeList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
