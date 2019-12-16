package com.gdkm.service.impl;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.Repository.CourseCommentReposiyory;
import com.gdkm.Repository.UserRepository;
import com.gdkm.converter.CourseToCourseDotConverter;
import com.gdkm.dto.CourseCommentDto;
import com.gdkm.model.Admin;
import com.gdkm.model.CourseComment;
import com.gdkm.model.User;
import com.gdkm.service.CourseCommentService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCommentServiceImpl implements CourseCommentService {

    @Autowired
    private CourseCommentReposiyory courseCommentReposiyory;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<CourseCommentDto> list(Pageable pageable) {
        Page<CourseComment> courseCommentPage = courseCommentReposiyory.findAll(pageable);
        List<CourseCommentDto> convert = CourseToCourseDotConverter.convert(courseCommentPage.getContent());
        for (CourseCommentDto courseCommentDto:convert){
            if (courseCommentDto.getAdminId()!=null) {
                Admin admin = adminRepository.findOne(courseCommentDto.getAdminId());
                courseCommentDto.setAdmin(admin);
            }
            User user = userRepository.findOne(courseCommentDto.getUserId());
            courseCommentDto.setUser(user);
        }
        return new PageImpl<CourseCommentDto>(convert, pageable, courseCommentPage.getTotalElements());
    }

    @Override
    public void update(Integer ccId, String adminContent,Admin admin) {
        CourseComment courseComment = courseCommentReposiyory.findOne(ccId);
        if (adminContent.equals("")){
            courseComment.setAdminContent(null);
        }else{
            courseComment.setAdminId(admin.getAdminId());
            courseComment.setAdminContent(adminContent);
        }
        courseCommentReposiyory.save(courseComment);
    }
}
