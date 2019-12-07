package com.gdkm.controller;

import com.gdkm.dto.CourseCommentDto;
import com.gdkm.model.CourseComment;
import com.gdkm.model.User;
import com.gdkm.service.CourseCommentService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(value = "前端教学反馈", tags = "前端教学反馈")
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

    @PostMapping("/byuser/comment")
    public ResultVO add(Integer grade,String userContent){
        return ResultVOUtil.success(courseCommentService.add(grade, userContent));
    }


}
