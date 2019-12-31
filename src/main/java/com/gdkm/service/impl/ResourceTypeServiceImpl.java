package com.gdkm.service.impl;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.Repository.ResourceTypeRepository;
import com.gdkm.converter.ResourceTypeToRTDtoConverter;
import com.gdkm.dto.ResourceTypeDto;
import com.gdkm.model.Admin;
import com.gdkm.model.ResourceType;
import com.gdkm.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional //必须添加这2个注解
public class ResourceTypeServiceImpl implements ResourceTypeService {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void add(ResourceType resourceType) {
        resourceTypeRepository.save(resourceType);
    }

    @Override
    public Page<ResourceTypeDto> list(Pageable pageable, String rtTitle) {
        Page<ResourceType> resourceTypePage;

        if (!(rtTitle == null || rtTitle.equals(""))) {
            rtTitle = '%' + rtTitle + '%';
            resourceTypePage = resourceTypeRepository.findResourceTypesByRtTitleLike(pageable, rtTitle);
        } else {
            resourceTypePage = resourceTypeRepository.findAll(pageable);
        }
        List<ResourceTypeDto> resourceTypeDtoList = ResourceTypeToRTDtoConverter.convert(resourceTypePage.getContent());
        for (ResourceTypeDto reourceType : resourceTypeDtoList) {
            Admin admin = adminRepository.findOne(reourceType.getRtId());
        }

        return new PageImpl<ResourceTypeDto>(resourceTypeDtoList, pageable, resourceTypePage.getTotalElements());
    }

    @Override
    public List<ResourceType> selectResourceType() {

        return resourceTypeRepository.findAll();

    }

    @Override
    public ResourceType oneResourceType(Integer resId) {

        return resourceTypeRepository.findOne(resId);

    }

    @Override
    public ResourceType update(ResourceType resourceType) {

        return resourceTypeRepository.save(resourceType);

    }

    @Override
    public void delete(Integer rtId) {
        resourceTypeRepository.delete(rtId);
    }

    @Override
    public Page<ResourceType> getPageSort(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page - 1, size);
        return resourceTypeRepository.findAll(pageable);
    }

}
