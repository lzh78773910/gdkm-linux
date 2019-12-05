package com.gdkm.service;

import com.gdkm.dto.ResourceTypeDto;
import com.gdkm.model.ResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourceTypeService {

    void add(ResourceType resourceType);

    Page<ResourceTypeDto> list(Pageable pageable, String rtTitle);

    List<ResourceType> selectResourceType();
}
