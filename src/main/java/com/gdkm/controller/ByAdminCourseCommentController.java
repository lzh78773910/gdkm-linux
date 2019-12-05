package com.gdkm.controller;

import com.gdkm.config.projectUrl;
import com.gdkm.dto.CourseCommentDto;
import com.gdkm.dto.VideoDto;
import com.gdkm.model.Admin;
import com.gdkm.service.CourseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Map;
@ApiIgnore
@Controller
@RequestMapping("/byadmin/coursecomment")
public class ByAdminCourseCommentController {
    @Autowired
    public com.gdkm.config.projectUrl projectUrl;
    @Autowired
    private CourseCommentService courseCommentService;


    @GetMapping("/list")
    public ModelAndView list(
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size ,Map map) {
        if (page<=1){
            page=1;
        }
        //按时间排序
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        PageRequest request = new PageRequest(page - 1, size,sort);
        Page<CourseCommentDto> courseCommentDtoPage = courseCommentService.list(request);
        map.put("courseCommentDtoPage", courseCommentDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("TotalPage", courseCommentDtoPage.getTotalPages());
        ModelAndView modelAndView=new ModelAndView("admin/coursecomment/list");
        return modelAndView;
    }


    @PostMapping("/update")
    public ModelAndView update(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "ccId")Integer ccId ,
            @RequestParam(value = "adminContent") String adminContent,
            HttpSession session
    ){
        Admin admin = (Admin) session.getAttribute("admin");
        courseCommentService.update(ccId,adminContent,admin);
        ModelAndView modelAndView=new ModelAndView("redirect:"+projectUrl.getLinux()+"/byadmin/coursecomment/list?page="+page);
        return modelAndView;
    }
}
