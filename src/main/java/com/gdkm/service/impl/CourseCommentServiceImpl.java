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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public Page<CourseCommentDto> myList(Integer currentPage,Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        PageRequest pageable = new PageRequest(currentPage - 1, pageSize,sort);
        Subject subject = SecurityUtils.getSubject();
        User userSession=(User)subject.getPrincipal();
        Page<CourseComment> courseCommentPage = courseCommentReposiyory.queryCourseCommentsByUserId(userSession.getUserId(),pageable);
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
    public void delete(Integer commentId) {
        Subject subject = SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        CourseComment courseComment = courseCommentReposiyory.findOne(commentId);
        if(courseComment.getUserId()==user.getUserId()){
            courseCommentReposiyory.delete(commentId);
        }
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

    @Override
    public CourseComment add(Integer grade, String userContent) {
        Subject subject = SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();

        CourseComment courseComment=new CourseComment();
        courseComment.setUserId(user.getUserId());
        courseComment.setGrade(grade);
        courseComment.setUserContent(userContent);

        CourseComment save = courseCommentReposiyory.save(courseComment);

        return save;
    }
}
