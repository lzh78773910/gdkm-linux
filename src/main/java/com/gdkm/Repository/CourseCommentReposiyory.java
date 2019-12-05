package com.gdkm.Repository;

import com.gdkm.model.CourseComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCommentReposiyory extends JpaRepository<CourseComment,Integer> {
}
