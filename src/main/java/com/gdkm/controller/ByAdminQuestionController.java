package com.gdkm.controller;

import com.gdkm.config.projectUrl;
import com.gdkm.dto.CommentDto;
import com.gdkm.dto.QuestionDto;
import com.gdkm.model.Question;
import com.gdkm.service.CommentService;
import com.gdkm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/byadmin/question/")
public class ByAdminQuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    public projectUrl projectUrl ;

    //问题添加页面
//    @GetMapping("/add")
//    public ModelAndView questionView() {
//        return new ModelAndView("/admin/question/add");
//    }

    //问题添加功能
//    @PostMapping("/addQuestion")
//    public ModelAndView addQuestion(
//            @RequestParam(value = "title", defaultValue = "问题标题为空") String title,
//            @RequestParam(value = "description") String description,
//            HttpSession session
//    ) {
//        Admin admin = (Admin) session.getAttribute("admin");
//        Question question = new Question();
//        question.setTitle(title);
//        question.setDescription(description);
//        question.setCreator(admin.getAdminId());
//        questionService.addQuestion(question);
//        System.out.println("问题创建成功");
//        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/question/list");
//    }

    //课后讨论列表
    @GetMapping("/list")
    public ModelAndView showSortPage(
            @RequestParam(value = "title",required = false)String title,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map map
    ) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        PageRequest request = new PageRequest(page - 1, size, sort);
        Page<QuestionDto> questionDtoPage = questionService.list(request,title);

        map.put("questionDtoPage",questionDtoPage);
        map.put("currentPage",page);
        map.put("size",size);
        map.put("TotalPage",questionDtoPage.getTotalPages());
        map.put("title",title);

        return new ModelAndView("admin/question/list", map);
    }

    //删除问题
    @GetMapping("/deleteQuestion")
    public ModelAndView deleteQuestion(
            @RequestParam(value = "qId", required = false) Integer qId
    ) {
        questionService.deleteQuestionById(qId);
        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/question/list");
    }

    //课后讨论问题查看
    @GetMapping("/detail")
    public ModelAndView showQuestionDetail(
            @RequestParam(value = "qId",required = false) Integer qId,
            Map map
    ){
        Question questionList = questionService.findQuestionById(qId);
        List<CommentDto> comments = commentService.listByQid(qId);
        map.put("questionList",questionList);
        map.put("comments",comments);

        return new ModelAndView("admin/question/detail",map);
    }


}
