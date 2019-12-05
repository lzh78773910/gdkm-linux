package com.gdkm.controller;

import com.gdkm.dto.CourseCommentDto;
import com.gdkm.model.CourseComment;
import com.gdkm.service.CourseCommentService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "前端课程评论", tags = "前端课程评论")
@RestController
//@RequestMapping("/")
public class CourseCommentController {
    @Autowired
    private CourseCommentService courseCommentService;

    @GetMapping("/commentList/{currentPage}/{pageSize}")
    public ResultVO commentList(@PathVariable(value = "currentPage",required = false) Integer currentPage,
                                @PathVariable(value = "pageSize",required = false) Integer pageSize){
        //按时间排序
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        PageRequest request = new PageRequest(currentPage - 1, pageSize,sort);
        Page<CourseCommentDto> courseCommentDtoPage = courseCommentService.list(request);
        return ResultVOUtil.success(courseCommentDtoPage);
    }

}
