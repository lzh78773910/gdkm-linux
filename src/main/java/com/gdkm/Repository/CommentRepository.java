package com.gdkm.Repository;

import com.gdkm.model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByParentIdLike(Integer parentId, Sort sort);

}
