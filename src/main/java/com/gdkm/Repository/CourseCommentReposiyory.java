package com.gdkm.Repository;

import com.gdkm.model.CourseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseCommentReposiyory extends JpaRepository<CourseComment,Integer> {
    Page<CourseComment>  queryCourseCommentsByUserId(Integer userId, Pageable pageable);
}
