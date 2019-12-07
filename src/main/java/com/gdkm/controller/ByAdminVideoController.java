package com.gdkm.controller;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.config.projectUrl;
import com.gdkm.dto.VideoDto;
import com.gdkm.model.Admin;
import com.gdkm.model.User;
import com.gdkm.model.Video;
import com.gdkm.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
@ApiIgnore()
@Controller
@RequestMapping("/byadmin/video")
public class ByAdminVideoController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private VideoService videoService;
    @Autowired
    public projectUrl projectUrl;

    @GetMapping("/main")
    public ModelAndView main(
            @RequestParam(value = "videoTitle",required = false) String videoTitle,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size, Map map) {

        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        PageRequest request = new PageRequest(page - 1, size,sort);
        Page<VideoDto> videoDtoPage = videoService.list(request,videoTitle);



        map.put("videoDtoPage", videoDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("TotalPage", videoDtoPage.getTotalPages());
        map.put("videoTitle", videoTitle);


        ModelAndView modelAndView = new ModelAndView("admin/video/main", map);
        return modelAndView;
    }

    @GetMapping("/one")
    public ModelAndView one(@RequestParam(value = "videoId") Integer videoId, Map map) {
        VideoDto videoDto = videoService.one(videoId);
        map.put("videoDto", videoDto);
        ModelAndView modelAndView = new ModelAndView("admin/video/video", map);
        return modelAndView;
    }

    @RequestMapping(value = "/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam(value = "coursewareUrl") String coursewareUrl) throws IOException  {
        String filePath = projectUrl.getKejianUrl()+coursewareUrl;
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));

    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("admin/video/add");
        return modelAndView;
    }

    @PostMapping("/addVideo")
    @Transactional
    public ModelAndView addVideo(@RequestParam(value = "videoTitle",defaultValue = "标题为空")String videoTitle,
                                 @RequestParam(value ="videoText",defaultValue = "讲义为空")String videoText,
                                 @RequestParam("videoImg") MultipartFile videoImg,
                                 @RequestParam(value = "file1KeJian",required = false) MultipartFile file1KeJian,
                                 Map map, HttpSession session) throws IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        Video video=new Video();
        video.setAdminId(admin.getAdminId());
        video.setVideoTitle(videoTitle);
        video.setVideoText(videoText);
        video.setViewNum(0);
        if (!file1KeJian.getOriginalFilename().equals("")) {
            //处理文件
            //获取的源文件的名称
            String fileName = file1KeJian.getOriginalFilename();
            //找到文件的后缀
            int lastIndexOf = fileName.lastIndexOf(".");
            String houzhui = fileName.substring(lastIndexOf);
            fileName= UUID.randomUUID().toString()+houzhui;
            //找到目标目录
            String contextPath = projectUrl.getKejianUrl();
            //完成上传文件的操作
            file1KeJian.transferTo(new File(contextPath  + fileName));
            video.setCoursewareUrl(projectUrl.getKejian()+fileName);
        }

        if (!videoImg.getOriginalFilename().equals("")) {
            //处理文件
            //获取的源文件的名称
            String fileName = videoImg.getOriginalFilename();
            //找到文件的后缀
            int lastIndexOf = fileName.lastIndexOf(".");
            String houzhui = fileName.substring(lastIndexOf);
            fileName= UUID.randomUUID().toString()+houzhui;
            //找到目标目录
            String contextPath = projectUrl.getImgUrl();
            //完成上传文件的操作
            videoImg.transferTo(new File(contextPath  + fileName));
            video.setVideoImg(projectUrl.getImg()+fileName);
        }



        videoService.add(video);
        ModelAndView modelAndView=new ModelAndView("redirect:"+projectUrl.getLinux()+"/byadmin/video/main");
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView delete(
            @RequestParam(value = "videoTitle",required = false) String videoTitle,
            @RequestParam(value = "videoId") Integer videoId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size, Map map) {
        videoService.delete(videoId);
        PageRequest request = new PageRequest(page - 1, size);
        Page<VideoDto> videoDtoPage = videoService.list(request,videoTitle);
        map.put("videoDtoPage", videoDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("TotalPage", videoDtoPage.getTotalPages());
        ModelAndView modelAndView = new ModelAndView("admin/video/main", map);
        return modelAndView;
    }

    @GetMapping("/update")
    public ModelAndView update(@RequestParam(value = "videoId") Integer videoId,Map map) {
        Video video = videoService.onevideo(videoId);
        map.put("video",video);
        ModelAndView modelAndView = new ModelAndView("admin/video/update",map);
        return modelAndView;
    }

    @PostMapping("/updateVideo")
    @Transactional
    public ModelAndView updateVideo( @RequestParam(value = "videoId")Integer videoId,
                                 @RequestParam(value = "videoTitle",defaultValue = "标题为空")String videoTitle,
                                 @RequestParam(value ="videoText",defaultValue = "讲义为空")String videoText,
                                     @RequestParam("videoImg") MultipartFile videoImg,
                                 @RequestParam(value = "file1KeJian",required = false) MultipartFile file1KeJian,
                                 Map map, HttpSession session) throws IOException {

        Video video = videoService.onevideo(videoId);
        video.setVideoTitle(videoTitle);
        video.setVideoText(videoText);
        if (!file1KeJian.getOriginalFilename().equals("")) {
            //处理文件
            //获取的源文件的名称
            String fileName = file1KeJian.getOriginalFilename();
            //找到文件的后缀
            int lastIndexOf = fileName.lastIndexOf(".");
            String houzhui = fileName.substring(lastIndexOf);
            fileName= UUID.randomUUID().toString()+houzhui;
            //找到目标目录
            String contextPath = projectUrl.getKejianUrl();
            //完成上传文件的操作
            file1KeJian.transferTo(new File(contextPath  + fileName));
            video.setCoursewareUrl(projectUrl.getKejian()+fileName);
        }
        if (!videoImg.getOriginalFilename().equals("")) {
            //处理文件
            //获取的源文件的名称
            String fileName = videoImg.getOriginalFilename();
            //找到文件的后缀
            int lastIndexOf = fileName.lastIndexOf(".");
            String houzhui = fileName.substring(lastIndexOf);
            fileName= UUID.randomUUID().toString()+houzhui;
            //找到目标目录
            String contextPath = projectUrl.getImgUrl();
            //完成上传文件的操作
            videoImg.transferTo(new File(contextPath  + fileName));
            video.setVideoImg(projectUrl.getImg()+fileName);
        }
        videoService.update(video);
        ModelAndView modelAndView=new ModelAndView("redirect:"+projectUrl.getLinux()+"/byadmin/video/update?videoId="+videoId);
        return modelAndView;
    }
}

