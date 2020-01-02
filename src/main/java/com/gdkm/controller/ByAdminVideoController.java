package com.gdkm.controller;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.config.projectUrl;
import com.gdkm.dto.VideoDto;
import com.gdkm.model.Admin;
import com.gdkm.model.User;
import com.gdkm.model.Video;
import com.gdkm.model.VideoItem;
import com.gdkm.service.VideoService;
import com.gdkm.utils.UCloudProvider;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
//@ApiIgnore()
@Controller
@RequestMapping("/byadmin/video")
public class ByAdminVideoController {

    @Autowired
    private UCloudProvider uCloudProvider;
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
    public ModelAndView one(@RequestParam(value = "videoId") Integer videoId,@RequestParam(value = "viId",required = false) Integer viId, Map map) {

        VideoDto videoDto = videoService.one(videoId);
        VideoItem item = new VideoItem();
        if (viId==null){
            List<VideoItem> videoItem = videoDto.getVideoItem();
            if (videoItem.size()>1){
                item = videoItem.get(0);
            }
        }else{
           item = videoService.Item(viId);
        }
        map.put("videoDto", videoDto);
        map.put("item", item);
        ModelAndView modelAndView = new ModelAndView("admin/video/video", map);
        return modelAndView;
    }

    @RequestMapping(value = "/download")
    public void download(@RequestParam(value = "coursewareUrl") String coursewareUrl) throws IOException  {
        uCloudProvider.getStream(coursewareUrl);
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
            String upload = uCloudProvider.upload(file1KeJian.getInputStream(), file1KeJian.getContentType(), file1KeJian.getOriginalFilename(), projectUrl.getKejianUcloud());
            video.setCoursewareUrl(upload);
        }
        if (!videoImg.getOriginalFilename().equals("")) {
            String upload = uCloudProvider.upload(videoImg.getInputStream(), videoImg.getContentType(), videoImg.getOriginalFilename(), projectUrl.getImgUcloud());
            video.setVideoImg(upload);
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
            String upload = uCloudProvider.upload(file1KeJian.getInputStream(), file1KeJian.getContentType(), file1KeJian.getOriginalFilename(), projectUrl.getKejianUcloud());
            video.setCoursewareUrl(upload);
        }
        if (!videoImg.getOriginalFilename().equals("")) {
            String upload = uCloudProvider.upload(videoImg.getInputStream(), videoImg.getContentType(), videoImg.getOriginalFilename(), projectUrl.getImgUcloud());
            video.setVideoImg(projectUrl.getImg()+upload);
        }
        videoService.update(video);
        ModelAndView modelAndView=new ModelAndView("redirect:"+projectUrl.getLinux()+"/byadmin/video/update?videoId="+videoId);
        return modelAndView;
    }


    @PostMapping("/additem")
    public ModelAndView additem( @RequestParam(value = "videoId")Integer videoId,
                                 @RequestParam(value = "title")String title,
                                     @RequestParam(value = "file",required = true) MultipartFile file,
                                     Map map) throws IOException {
        videoService.additem(videoId,title,file);
        ModelAndView modelAndView=new ModelAndView("redirect:"+projectUrl.getLinux()+"/byadmin/video/one?videoId="+videoId);
        return modelAndView;
    }
}

