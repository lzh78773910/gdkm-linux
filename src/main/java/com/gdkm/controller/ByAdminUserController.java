package com.gdkm.controller;

import com.gdkm.model.User;
import com.gdkm.service.UserService;
import com.gdkm.vo.PageVo;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Controller
@RequestMapping("/byadmin/user")
public class ByAdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ModelAndView user(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map map){
        PageRequest request=new PageRequest(page-1,size);
        Page<User> userPage = userService.userPage(request);
        map.put("userPage",userPage);
        map.put("currentPage",page);
        map.put("size",size);
        map.put("TotalPage",userPage.getTotalPages());

        ModelAndView modelAndView=new ModelAndView("admin/user/user",map);
        return  modelAndView;
    }

    @ApiOperation(value = "用户列表")
    @ResponseBody
    @GetMapping("/lists")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest request=new PageRequest(page-1,size);
        Page<User> userPage = userService.userPage(request);
        PageVo<User> pageVo=new PageVo();
        pageVo.setCurrentPage(page);
        pageVo.setTotalPage(userPage.getTotalPages());
        pageVo.setList(userPage.getContent());
        ResultVO resultVO= new ResultVO<>();
        resultVO.setData(pageVo);
        resultVO.setMsg("true");
        return resultVO;
    }


}
