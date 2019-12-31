package com.gdkm.Repository;

import com.gdkm.model.Comment;
import com.gdkm.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    Page<Resource> findByResTitleLike(Pageable pageable, String videoTitle);

    @Modifying   //必须添加这2个注解
    @Query(value = "delete from resource where rt_id = ?1", nativeQuery = true)
    void deleteResource(@Param(value = "rtId") Integer rtId);

    List<Resource> findByRtIdLike(Integer rtId);
}
