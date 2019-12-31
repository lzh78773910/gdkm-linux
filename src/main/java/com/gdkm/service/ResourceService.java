package com.gdkm.service;

import com.gdkm.dto.ResourceDto;
import com.gdkm.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ResourceService {


    Page<ResourceDto> list(PageRequest pageable, String resTitle);

    Resource add(Resource resource);

    Resource oneResource(Integer resId);

    Resource update(Resource resource);

    void delete(Integer resId);


    void deleteResource(Integer rtId);

    Page<Resource> getPageSort(Integer page, Integer size);

    List<ResourceDto> findResourceById(Integer rtId);
}
