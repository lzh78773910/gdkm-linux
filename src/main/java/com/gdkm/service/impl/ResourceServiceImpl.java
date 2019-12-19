package com.gdkm.service.impl;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.Repository.ResourceRepository;
import com.gdkm.converter.ResourceToResourceDtoConverter;
import com.gdkm.dto.ResourceDto;
import com.gdkm.model.Admin;
import com.gdkm.model.Resource;
import com.gdkm.model.ResourceType;
import com.gdkm.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional //必须添加这2个注解
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AdminRepository adminRepository;

    //资源文件上传
    @Override
    public Resource add(Resource resource) {
        return resourceRepository.save(resource);
    }

    //教学资源更新
    @Override
    public Resource oneResource(Integer resId) {
        Resource resource = resourceRepository.findOne(resId);
        return resource;
    }

    //教学资源更新
    @Override
    public Resource update(Resource resource) {
        return resourceRepository.save(resource);
    }

    //教学资源删除
    @Override
    public void delete(Integer resId) {
        resourceRepository.delete(resId);
    }

    @Override
    public void deleteResource(Integer rtId) {
        resourceRepository.deleteResource(rtId);
    }

    //资源管理分页
    @Override
    public Page<ResourceDto> list(PageRequest pageable, String resTitle) {
        Page<Resource> resourcePage;
        if (!(resTitle == null || resTitle.equals(""))) {
            resTitle = '%' + resTitle + '%';
            resourcePage = resourceRepository.findByResTitleLike(pageable, resTitle);
        } else {
            resourcePage = resourceRepository.findAll(pageable);
        }
        List<ResourceDto> resourceDtoList = ResourceToResourceDtoConverter.convert(resourcePage.getContent());
        for (ResourceDto resourceDto : resourceDtoList) {
            Admin admin = adminRepository.findOne(resourceDto.getAdminId());
            resourceDto.setAdmin(admin);
        }
        return new PageImpl<ResourceDto>(resourceDtoList, pageable, resourcePage.getTotalElements());
    }

    //前段资源展示
    @Override
    public Page<Resource> getPageSort(Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updatetime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        return resourceRepository.findAll(pageable);
    }

}
