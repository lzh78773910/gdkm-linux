package com.gdkm.Repository;

import com.gdkm.model.VideoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoItemRepository extends JpaRepository<VideoItem,Integer> {

}
