package com.gdkm.Repository;

import com.gdkm.model.Resource;
import com.gdkm.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceType, Integer> {

    void save(Resource resource);

}
