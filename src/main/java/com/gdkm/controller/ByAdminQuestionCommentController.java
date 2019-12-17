package com.gdkm.controller;

import com.gdkm.dto.CommentDto;
import com.gdkm.model.Admin;
import com.gdkm.model.Comment;
import com.gdkm.service.CommentService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/byadmin/comment/")
public class ByAdminQuestionCommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    public com.gdkm.config.projectUrl projectUrl;

    //添加课后讨论回复
    @ResponseBody
    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    public ResultVO addComment(
            @RequestBody CommentDto commentDto,
            HttpSession session
    ){
        Admin admin = (Admin) session.getAttribute("admin");
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setCommentator(admin.getAdminId());
        commentService.addCommentByUser(comment);
        return ResultVOUtil.success();
    }

    //根据ID查询课后讨论回复
    @ResponseBody
    @RequestMapping(value = "/commentDetail/{cId}" , method = RequestMethod.GET)
    public ResultVO<List<CommentDto>> comments(@PathVariable(name = "cId") Integer cId){
        List<CommentDto> commentDtos = commentService.listByQid(cId);
        return ResultVOUtil.success(commentDtos);
    }

}
