package com.gdkm.service;

import com.gdkm.dto.ResourceDto;
import com.gdkm.model.Resource;
import com.gdkm.model.ResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ResourceService {


    Page<ResourceDto> list(PageRequest pageable, String resTitle);

    Resource add(Resource resource);

    Resource oneResource(Integer resId);

    Resource update(Resource resource);

    void delete(Integer resId);


    void deleteResource(Integer rtId);
}
