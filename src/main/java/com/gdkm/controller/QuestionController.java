package com.gdkm.controller;

import com.gdkm.dto.CommentDto;
import com.gdkm.dto.QuestionDto;
import com.gdkm.model.Question;
import com.gdkm.service.CommentService;
import com.gdkm.service.QuestionService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "课后讨论", tags = "课后讨论")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    public com.gdkm.config.projectUrl projectUrl;

    //问题分页展示
    /**
     * 分页反馈
     *
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("课后讨论")
    @GetMapping("/question/{page}/{size}")
    @ResponseBody
    public ResultVO showSortPage(@PathVariable(value = "page", required = false) Integer page, @PathVariable(value = "size", required = false) Integer size) {
        Page<QuestionDto> questionPage = questionService.getPageSort(page, size);
        return ResultVOUtil.success(questionPage);
    }

    /**
     * 添加问题
     *
     * @param title
     * @param description
     * @return
     */
    //问题添加功能
    @ApiOperation(value = "添加问题")
    @PostMapping("/byuser/question")
//    @PostMapping("/question")
    public ResultVO addQuestion(
            @RequestParam(value = "title", defaultValue = "问题标题为空") String title,
            @RequestParam(value = "description") String description
    ) {
        questionService.addUserQuestion(title,description);
        return ResultVOUtil.success();
    }

    /**
     * 删除问题
     * @param qId
     * @return
     */
    @ApiOperation("删除问题")
    @DeleteMapping("/byuser/question/{qId}")
    public ResultVO deleteQuestion(@PathVariable(value = "qId",required = true)Integer qId){
        questionService.deleteQuestionById(qId);
        return ResultVOUtil.success();
    }

    //课后讨论回复展示
    /**
     * 课后讨论回复展示
     * @param qId
     * @return
     */
    @ApiOperation("问题讨论展示")
    @GetMapping("/comment/{qId}")
    @ResponseBody
    public ResultVO showSortComment(@PathVariable(value = "qId")Integer qId) {
        List<CommentDto> commentDto = commentService.listByQid(qId);
        return ResultVOUtil.success(commentDto);
    }

    //课后讨论添加回复
    /**
     * 课后讨论添加回复
     * @param parentId
     * @param content
     * @return
     */
    @ApiOperation("问题讨论添加")
    @PostMapping(value = "/byuser/addCommentByUser")
    public ResultVO addCommentByUser(Integer parentId,String content){
        return ResultVOUtil.success(commentService.addComment(parentId,content));
    }

}
