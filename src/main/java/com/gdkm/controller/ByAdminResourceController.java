package com.gdkm.controller;

import com.gdkm.dto.ResourceTypeDto;
import com.gdkm.model.Admin;
import com.gdkm.model.Resource;
import com.gdkm.model.ResourceType;
import com.gdkm.service.ResourceService;
import com.gdkm.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/byadmin/resource/")
public class ByAdminResourceController {

    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    public com.gdkm.config.projectUrl projectUrl;

    //教学资源类型
    @GetMapping("/type")
    public ModelAndView queryType(@RequestParam(name = "rtTitle", required = false) String rtTitle,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  Map map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ResourceTypeDto> resourceTypeDtoPage = resourceTypeService.list(request, rtTitle);

        map.put("rtTitle", rtTitle);
        map.put("resourceTypeDtoPage", resourceTypeDtoPage);
        map.put("TotalPage", resourceTypeDtoPage.getTotalPages());
        ModelAndView modelAndView = new ModelAndView("admin/resource/type", map);
        return modelAndView;
    }

    //教学资源类型删除


    //添加教学资源类型
    @PostMapping("/addType")
    public ModelAndView addType(@RequestParam(value = "rtTitle", defaultValue = "标题为空") String rtTitle, HttpSession session, Map map) {
        Admin admin = (Admin) session.getAttribute("admin");
        ResourceType resourceType = new ResourceType();
        resourceType.setRtTitle(rtTitle);
        resourceTypeService.add(resourceType);
        ModelAndView modelAndView = new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/type");
        return modelAndView;
    }


    //教学资源文件
    @GetMapping("/file")
    public ModelAndView add(Map map) {
        List<ResourceType> resourceTypeDto = resourceTypeService.selectResourceType();
        map.put("resourceTypeDto", resourceTypeDto);
        ModelAndView modelAndView = new ModelAndView("admin/resource/file");
        return modelAndView;
    }

    //教学资源文件上传
    @PostMapping("/addFile")
    public ModelAndView addFile(@RequestParam(value = "resTitle", defaultValue = "标题为空") String resTitle,
                                @RequestParam(value = "resContent", defaultValue = "内容为空") String resContent,
                                @RequestParam(value = "resUrl") MultipartFile resUrl,
                                @RequestParam(value = "rtId", required = false) Integer rtId, HttpSession session
    ) throws IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        Resource resource = new Resource();
        resource.setAdminId(admin.getAdminId());
        resource.setResTitle(resTitle);
        resource.setResContent(resContent);
        resource.setViewNum(0);
        resource.setRtId(rtId);
        if (!resUrl.getOriginalFilename().equals("")) {
            //处理文件
            //获取的源文件的名称
            String fileName = resUrl.getOriginalFilename();
            //找到文件的后缀
            int lastIndexOf = fileName.lastIndexOf(".");
            String houzhui = fileName.substring(lastIndexOf);
            fileName = UUID.randomUUID().toString() + houzhui;
            //找到目标目录
            String contextPath = projectUrl.getKejianUrl();
            //完成上传文件的操作
            resUrl.transferTo(new File(contextPath + fileName));
            resource.setResUrl(projectUrl.getKejian()+fileName);
        }

        resourceService.add(resource);
        System.out.println("上传课件成功");
        ModelAndView modelAndView = new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/type");
        return modelAndView;
    }


}

