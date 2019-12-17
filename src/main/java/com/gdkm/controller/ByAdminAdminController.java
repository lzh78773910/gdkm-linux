package com.gdkm.controller;


import com.gdkm.Repository.AdminRepository;
import com.gdkm.config.projectUrl;
import com.gdkm.enums.ResultEnum;
import com.gdkm.model.Admin;
import com.gdkm.service.AdminService;
import com.gdkm.utils.AjaxResult;
import com.gdkm.utils.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
@ApiIgnore
@Controller
@RequestMapping("/byadmin/admin")
public class ByAdminAdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository repository;

    @Autowired
    public projectUrl projectUrl;

    @Autowired
    private UCloudProvider uCloudProvider;

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView=new ModelAndView("admin/login");
        return modelAndView;
    }


    @PostMapping("/loginPost")
    @ResponseBody
    public AjaxResult loginPost(@RequestParam("adminName")String adminName, @RequestParam("adminPass")String adminPass, HttpSession session, @ApiIgnore() Map map){
        Admin admin = adminService.longin(adminName, adminPass);
        AjaxResult ajaxResult=new AjaxResult();
        if (admin!=null){
            session.setAttribute("admin",admin);
            ajaxResult.setSuccess(true);
        }else {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage(ResultEnum.ADMIN_LONGIN_NAMEORPASS_FALSE.getMessage());
        }
        return ajaxResult;
    }
    /**
     * 注销
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        ModelAndView modelAndView=new ModelAndView("redirect:"+projectUrl.getLinux()+"/byadmin/admin/login");
        return modelAndView;
    }

    @GetMapping("/updata")
    public ModelAndView updata(HttpSession session,Map map){
        Admin admin = (Admin) session.getAttribute("admin");
        map.put("admin",admin);
        ModelAndView modelAndView=new ModelAndView("admin/admin/updata");
        return modelAndView;
    }

    @PostMapping("/updataPost")
    @ResponseBody
    public AjaxResult updatado(@RequestParam("adminName")String adminName, @RequestParam("adminPass")String adminPass,@RequestParam("adminPassOld")String adminPassOld, HttpSession session){
        AjaxResult ajaxResult=new AjaxResult();
        Admin byAdminName = repository.findByAdminName(adminName);
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin.getAdminName().equals(adminName)) {
            if (byAdminName != null) {
                if (byAdminName.getAdminPass().equals(adminPassOld)) {
                    byAdminName.setAdminPass(adminPass);
                    repository.save(byAdminName);
                    ajaxResult.setSuccess(true);
                } else {
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage(ResultEnum.ADMIN_LONGIN_NAMEORPASS_FALSE.getMessage());
                }
            } else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage(ResultEnum.ADMIN_LONGIN_NAMEORPASS_FALSE.getMessage());
            }
        }
        return ajaxResult;
    }


    @GetMapping("/main")
    public ModelAndView mian(HttpSession session,Map map){
        Admin admin = (Admin) session.getAttribute("admin");
        map.put("admin",admin);
        ModelAndView modelAndView=new ModelAndView("admin/admin/main");
        return modelAndView;
    }


    @PostMapping("/uploadfile")
    public ModelAndView uploadfile(@RequestParam("adminNickname")String adminNickname,@RequestParam("file")MultipartFile file, Map map, HttpSession session) throws IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        Admin byAdminName = repository.findByAdminName(admin.getAdminName());
        byAdminName.setAdminNickname(adminNickname);
        if (!file.getOriginalFilename().equals("")) {
            String upload = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename(), projectUrl.getImgUcloud());
            byAdminName.setAdminIcon(upload);
        }
        repository.save(byAdminName);
        map.put("admin",byAdminName);
        ModelAndView modelAndView=new ModelAndView("redirect:"+projectUrl.getLinux()+"/byadmin/admin/main");
        return modelAndView;
    }


    @GetMapping("/add")
    public ModelAndView add(HttpSession session,Map map){
        ModelAndView modelAndView=new ModelAndView("admin/admin/add");
        return modelAndView;
    }


    @PostMapping("/addPost")
    @ResponseBody
    public AjaxResult addPost(Admin admin){
        AjaxResult ajaxResult=new AjaxResult();

        Admin serviceAdmin = adminService.createAdmin(admin);

        if (serviceAdmin!=null){
            ajaxResult.setSuccess(true);
        }else {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage(ResultEnum.ADMIN_ADD_FALSE.getMessage());
        }

        return ajaxResult;
    }

}
