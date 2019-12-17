package com.gdkm.controller;

import com.gdkm.model.Admin;
import com.gdkm.model.Question;
import com.gdkm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/byadmin/question/")
public class ByAdminQuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    public com.gdkm.config.projectUrl projectUrl;

    //问题添加页面
    @GetMapping("/add")
    public ModelAndView questionView() {
        return new ModelAndView("/admin/question/add");
    }

    //问题添加功能
    @PostMapping("/addQuestion")
    public ModelAndView questionAdd(
            @RequestParam(value = "title", defaultValue = "问题标题为空") String title,
            @RequestParam(value = "description") String description,
            HttpSession session
    ) {
        Admin admin = (Admin) session.getAttribute("admin");
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(admin.getAdminId());
        questionService.addQuestion(question);
        System.out.println("问题创建成功");
        ModelAndView modelAndView = new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/question/list");
        return modelAndView;
    }

    //问题分页展示
    @GetMapping("/list")
    @ResponseBody
    public Page<Question> showSortPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map map
    ) {
        System.out.println("排序分页    page：" + page + "   size：" + size);
        return questionService.getPageSort(page, size);
    }


}
