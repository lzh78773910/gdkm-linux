package com.gdkm.service.impl;

import com.gdkm.Repository.AdminRepository;
import com.gdkm.Repository.VideoRepository;
import com.gdkm.converter.VideoTOVideoDtoConverter;
import com.gdkm.dto.VideoDto;
import com.gdkm.model.Admin;
import com.gdkm.model.Video;
import com.gdkm.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoServiceImpl implements VideoService {


    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AdminRepository adminRepository;


    @Override
    public Page<VideoDto> list(Pageable pageable,String videoTitle) {
        Page<Video> videoPage;
        if(!(videoTitle==null||videoTitle.equals(""))){
            videoTitle='%' +videoTitle+ '%';
            videoPage = videoRepository.findByVideoTitleLike(pageable,videoTitle);
        }else {
            videoPage = videoRepository.findAll(pageable);
        }
        List<VideoDto> videoDtoList = VideoTOVideoDtoConverter.convert(videoPage.getContent());
        for (VideoDto video:videoDtoList){
            Admin admin = adminRepository.findOne(video.getAdminId());
            video.setAdmin(admin);
        }
        return new PageImpl<VideoDto>(videoDtoList, pageable, videoPage.getTotalElements());
    }

    @Override
    public void add(Video video) {
        videoRepository.save(video);
    }

    @Override
    public void delete(Integer videoId) {
        videoRepository.delete(videoId);
    }

    @Override
    public Video update(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public VideoDto one(Integer videoId) {
        Video video = videoRepository.findOne(videoId);
        VideoDto videoDto=new VideoDto();
        BeanUtils.copyProperties(video,videoDto);
        Admin admin = adminRepository.findOne(video.getAdminId());
        videoDto.setAdmin(admin);
        return videoDto;
    }

    @Override
    public Video onevideo(Integer videoId) {
        Video video = videoRepository.findOne(videoId);

        return video;
    }
}
