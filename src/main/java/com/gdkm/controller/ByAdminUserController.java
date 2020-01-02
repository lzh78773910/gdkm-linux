package com.gdkm.controller;

import com.gdkm.Repository.UserRepository;
import com.gdkm.model.User;
import com.gdkm.service.UserService;
import com.gdkm.utils.ResultVOUtil;
import com.gdkm.vo.PageVo;
import com.gdkm.vo.ResultVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
@ApiIgnore
@Controller
@RequestMapping("/byadmin/user")
public class ByAdminUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @ApiOperation("测试")
    @ResponseBody
    @RequestMapping("selectStudent/{idd}")
    public ResultVO<User> selectStudent(@PathVariable("idd") String id){
        User user=  userRepository.findByuserName(id);

        return ResultVOUtil.success(user);
    }



    @ResponseBody
    @RequestMapping("updateStudent/")
    @Transactional
    @Rollback(false)
    public String updataOne(@RequestParam("gg")Integer gg,@RequestParam("ff")String ff){
        userRepository.updateOne(gg,ff);
        return null;
    }




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
