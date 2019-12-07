package com.gdkm.converter;

import com.gdkm.dto.ResourceDto;
import com.gdkm.model.Resource;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceToResourceDtoConverter {
    public static ResourceDto convert(Resource resource) {

        ResourceDto resourceDto = new ResourceDto();
        BeanUtils.copyProperties(resource, resourceDto);
        return resourceDto;
    }

    public static List<ResourceDto> convert(List<Resource> resourceList) {
        return resourceList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
