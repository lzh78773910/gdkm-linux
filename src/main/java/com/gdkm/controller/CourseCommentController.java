package com.gdkm.controller;

import com.gdkm.dto.CourseCommentDto;
import com.gdkm.model.CourseComment;
import com.gdkm.model.User;
import com.gdkm.service.CourseCommentService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 分页反馈
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页反馈")
    @GetMapping("/comment/{currentPage}/{pageSize}")
    public ResultVO commentList(@PathVariable(value = "currentPage",required = false) Integer currentPage,
                                @PathVariable(value = "pageSize",required = false) Integer pageSize){
        //按时间排序
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        PageRequest request = new PageRequest(currentPage - 1, pageSize,sort);
        Page<CourseCommentDto> courseCommentDtoPage = courseCommentService.list(request);
        return ResultVOUtil.success(courseCommentDtoPage);
    }

    /**
     * 添加反馈
     * @param grade
     * @param userContent
     * @return
     */
    @ApiOperation(value = "添加反馈")
    @PostMapping("/byuser/comment")
    public ResultVO add(Integer grade,String userContent){
        return ResultVOUtil.success(courseCommentService.add(grade, userContent));
    }

    /**
     * 我的反馈
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "我的分页反馈")
    @GetMapping("/byuser/comment/{currentPage}/{pageSize}")
    public ResultVO myCommentList(@PathVariable(value = "currentPage",required = false) Integer currentPage,
                                @PathVariable(value = "pageSize",required = false) Integer pageSize){
        Page<CourseCommentDto> courseCommentDtos = courseCommentService.myList(currentPage, pageSize);
        return ResultVOUtil.success(courseCommentDtos);
    }

    @ApiOperation(value = "删除反馈")
    @DeleteMapping("/byuser/comment/{id}")
    public ResultVO delete(@PathVariable(value = "id",required = true) Integer commentId){
        courseCommentService.delete(commentId);
        return ResultVOUtil.success();
    }


}
