package com.gdkm.service.impl;

import com.gdkm.Repository.ResourceRepository;
import com.gdkm.model.Resource;
import com.gdkm.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public void add(Resource resource) {
        resourceRepository.save(resource);
    }
}
