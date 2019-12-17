package com.gdkm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/byadmin/questionComment/")
public class ByAdminCommentController {
    @GetMapping("/add")
    public ModelAndView commentView(){
        return new ModelAndView("/admin/question/add");
    }
}
