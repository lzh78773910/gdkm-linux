package com.gdkm.service;

import com.gdkm.dto.CourseCommentDto;
import com.gdkm.model.Admin;
import com.gdkm.model.CourseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseCommentService {
    Page<CourseCommentDto> list(Pageable pageable);

    void update(Integer ccId, String adminContent,Admin admin);

    CourseComment add(Integer grade, String userContent);

    public Page<CourseCommentDto> myList(Integer currentPage,Integer pageSize);

    void  delete(Integer commentId);
}
