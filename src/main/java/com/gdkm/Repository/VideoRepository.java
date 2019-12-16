package com.gdkm.Repository;

import com.gdkm.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,Integer> {

    Page<Video> findByVideoTitleLike(Pageable pageable, String videoTitle);
}
