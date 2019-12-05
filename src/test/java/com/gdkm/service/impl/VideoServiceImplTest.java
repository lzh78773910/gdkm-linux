package com.gdkm.service.impl;

import com.gdkm.dto.VideoDto;
import com.gdkm.model.User;
import com.gdkm.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VideoServiceImplTest {

    @Autowired
    private  VideoService videoService;


    @Test
    public void list() {
        PageRequest request=new PageRequest(0,3);
       // Page<VideoDto> userPage = videoService.list(request, @RequestParam(value = "videoTitle",required = false) String videoTitle,);
       // Assert.assertNotEquals(0,userPage.getTotalElements());
    }

    @Test
    public void add() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }
}