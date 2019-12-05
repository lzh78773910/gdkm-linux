package com.gdkm.Repository;

import com.gdkm.model.ResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceTypeRepository extends JpaRepository<ResourceType,Integer> {

    Page<ResourceType> findResourceTypesByRtTitleLike(Pageable pageable, String rtTitle);
//    Page<ResourceType> findResourceTypesByRtTitleLike(Pageable pageable,String rtTitle);

}
