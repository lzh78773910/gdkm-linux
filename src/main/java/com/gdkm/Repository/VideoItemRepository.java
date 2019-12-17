package com.gdkm.Repository;

import com.gdkm.model.VideoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoItemRepository extends JpaRepository<VideoItem,Integer> {
    List<VideoItem> findAllByVideoId (Integer videoId);
}
