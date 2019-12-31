package com.gdkm.Repository;

import com.gdkm.model.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoCommentRepository extends JpaRepository<VideoComment,Integer> {
    List<VideoComment> findByParentIdLike (Integer parentId);
}
