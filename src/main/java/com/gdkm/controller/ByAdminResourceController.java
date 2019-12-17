package com.gdkm.controller;

import com.gdkm.dto.ResourceDto;
import com.gdkm.dto.ResourceTypeDto;
import com.gdkm.model.Admin;
import com.gdkm.model.Resource;
import com.gdkm.model.ResourceType;
import com.gdkm.service.ResourceService;
import com.gdkm.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApiIgnore
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
    public ModelAndView queryType(
            @RequestParam(name = "rtTitle", required = false) String rtTitle,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ResourceTypeDto> resourceTypeDtoPage = resourceTypeService.list(request, rtTitle);

        map.put("rtTitle", rtTitle);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("resourceTypeDtoPage", resourceTypeDtoPage);
        map.put("TotalPage", resourceTypeDtoPage.getTotalPages());

        return new ModelAndView("/admin/resource/type", map);
    }

    //教学资源类型更新界面
    @GetMapping("/updateResType")
    public ModelAndView updateType(@RequestParam(value = "rtId",required = false)Integer rtId,Map map) {
        ResourceType resourceType = resourceTypeService.oneResourceType(rtId);
        map.put("resourceType",resourceType);
        ModelAndView modelAndView = new ModelAndView("admin/resource/updatetype",map);
        return modelAndView;
    }

    @GetMapping("/addtype")
    public ModelAndView addTypeView() {
        return new ModelAndView("admin/resource/addtype");
    }

    //添加教学资源类型
    @PostMapping("/addType")
    public ModelAndView addType(
            @RequestParam(value = "rtTitle", defaultValue = "标题为空") String rtTitle
    ) {
        ResourceType resourceType = new ResourceType();
        resourceType.setRtTitle(rtTitle);
        resourceTypeService.add(resourceType);
        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/type");
    }



    //教学资源类型更新
    @PostMapping("updateRT")
    public ModelAndView updateRT(
            @RequestParam(value = "rtTitle", defaultValue = "标题为空") String rtTitle,
            @RequestParam(value = "rtId") Integer rtId
    ) {
        ResourceType resourceType = resourceTypeService.oneResourceType(rtId);
        resourceType.setRtTitle(rtTitle);
        resourceTypeService.update(resourceType);
        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/type");
    }

    //教学资源类型下资源全部删除
    @GetMapping("/deleteResType")
    public ModelAndView deleteResType(@RequestParam(value = "rtId")Integer rtId){
        resourceService.deleteResource(rtId);
        resourceTypeService.delete(rtId);
        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/type");
    }

    //教学资源文件
    @GetMapping("/file")
    public ModelAndView add(Map map) {
        List<ResourceType> resourceTypeDto = resourceTypeService.selectResourceType();
        map.put("resourceTypeDto", resourceTypeDto);
        return new ModelAndView("admin/resource/file");
    }

    //教学资源管理页面与分页
    @GetMapping("/list")
    public ModelAndView listView(
            @RequestParam(value = "resTitle", required = false) String resTitle,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size, Map map
    ) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        PageRequest request = new PageRequest(page - 1, size, sort);
        Page<ResourceDto> resourceDtoPage = resourceService.list(request, resTitle);

        map.put("resourceDtoPage", resourceDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("TotalPage", resourceDtoPage.getTotalPages());
        map.put("resTitle", resTitle);

        return new ModelAndView("admin/resource/list", map);
    }

    //教学资源文件上传
    @PostMapping("/addFile")
    public ModelAndView addFile(
            @RequestParam(value = "resTitle", defaultValue = "标题为空") String resTitle,
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
            resource.setResUrl(projectUrl.getKejian() + fileName);
        }

        resourceService.add(resource);
        System.out.println("上传课件成功");
        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/list");
    }

    //教学资源文件更新页面
    @GetMapping("/update")
    public ModelAndView update(
            @RequestParam(value = "resId") Integer resId, Map map
    ) {
        Resource resource = resourceService.oneResource(resId);
        List<ResourceType> resourceTypeDto = resourceTypeService.selectResourceType();
        map.put("resourceTypeDto", resourceTypeDto);
        map.put("resource", resource);
        return new ModelAndView("admin/resource/update", map);
    }

    //更新资源文件
    @PostMapping("/updateResource")
    public ModelAndView updateResource(
            @RequestParam(value = "resId") Integer resId,
            @RequestParam(value = "resTitle") String resTitle,
            @RequestParam(value = "resContent") String resContent,
            @RequestParam(value = "resUrl") MultipartFile resUrl,
            @RequestParam(value = "rtId", required = false) Integer rtId
    ) throws IOException {
        Resource resource = resourceService.oneResource(resId);
        resource.setResTitle(resTitle);
        resource.setResContent(resContent);
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
            resource.setResUrl(projectUrl.getKejian() + fileName);
        }

        resourceService.update(resource);
        System.out.println("更新成功");

        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/list");
    }

    //资源文件删除
    @GetMapping("/delete")
    public ModelAndView deleteResource(
            @RequestParam(value = "resId") Integer resId,
            @RequestParam(value = "resTitle", required = false) String resTitle,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size, Map map
    ) {
        resourceService.delete(resId);
        PageRequest request = new PageRequest(page - 1, size);
        Page<ResourceDto> resourceDtoPage = resourceService.list(request, resTitle);
        map.put("videoDtoPage", resourceDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("TotalPage", resourceDtoPage.getTotalPages());
        return new ModelAndView("redirect:" + projectUrl.getLinux() + "/byadmin/resource/list", map);
    }

}

