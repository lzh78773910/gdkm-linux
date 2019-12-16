package com.gdkm.controller;


import com.gdkm.config.projectUrl;
import com.gdkm.dto.VideoDto;
import com.gdkm.service.VideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api
@Controller
@RequestMapping("/byadmin/video")
public class TestController {
    @Autowired
    private VideoService videoService;
    @Autowired
    public com.gdkm.config.projectUrl projectUrl;

    @GetMapping("/onetest")
    @ResponseBody
    public VideoDto one(@RequestParam(value = "videoId") Integer videoId) {
        VideoDto videoDto = videoService.one(videoId);
        return videoDto;
    }
}
