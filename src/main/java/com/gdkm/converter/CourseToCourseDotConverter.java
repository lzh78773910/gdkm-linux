package com.gdkm.converter;

import com.gdkm.dto.CourseCommentDto;
import com.gdkm.model.CourseComment;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CourseToCourseDotConverter {


    public static CourseCommentDto convert(CourseComment courseComment) {

        CourseCommentDto courseCommentDto = new CourseCommentDto();
        BeanUtils.copyProperties(courseComment, courseCommentDto);
        return courseCommentDto;
    }

    public static List<CourseCommentDto> convert(List<CourseComment> courseCommentList) {
        return courseCommentList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
