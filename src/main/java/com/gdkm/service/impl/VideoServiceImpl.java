package com.gdkm.service.impl;

import com.gdkm.Repository.*;
import com.gdkm.converter.VideoTOVideoDtoConverter;
import com.gdkm.dto.VideoCommentDto;
import com.gdkm.dto.VideoDto;
import com.gdkm.model.*;
import com.gdkm.service.VideoService;
import com.gdkm.utils.UCloudProvider;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {


    @Autowired
    private UCloudProvider uCloudProvider;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoCommentRepository videoCommentRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private VideoItemRepository videoItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public com.gdkm.config.projectUrl projectUrl;

    @Override
    public Page<VideoDto> list(Pageable pageable, String videoTitle) {
        Page<Video> videoPage;
        if (!(videoTitle == null || videoTitle.equals(""))) {
            videoTitle = '%' + videoTitle + '%';
            videoPage = videoRepository.findByVideoTitleLike(pageable, videoTitle);
        } else {
            videoPage = videoRepository.findAll(pageable);
        }
        List<VideoDto> videoDtoList = VideoTOVideoDtoConverter.convert(videoPage.getContent());
        for (VideoDto video : videoDtoList) {
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
        VideoDto videoDto = new VideoDto();
        BeanUtils.copyProperties(video, videoDto);
        List<VideoItem> itemList = videoItemRepository.findAllByVideoId(videoId);
        videoDto.setVideoItem(itemList);
        Admin admin = adminRepository.findOne(video.getAdminId());
        videoDto.setAdmin(admin);
        return videoDto;
    }

    public VideoItem Item(Integer viId) {
        VideoItem item = videoItemRepository.findOne(viId);
        return item;
    }

    @Override
    public void additem(Integer videoId, String title, MultipartFile file) throws IOException {
        VideoItem item = new VideoItem();
        item.setVideoId(videoId);
        item.setViTitle(title);
        if (!file.getOriginalFilename().equals("")) {
            String upload = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename(), projectUrl.getShipinUcloud());
            item.setViUrl(upload);
        }
        videoItemRepository.save(item);
    }

    @Override
    public Page<Video> getPage(Integer page, Integer size,String title,String sortStr) {
        Sort sort = new Sort(Sort.Direction.DESC, sortStr);
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Page<Video> videoPage;
        if (!(title == null || title.equals(""))) {
            title = '%' + title + '%';
            videoPage = videoRepository.findByVideoTitleLike(pageable, title);
        } else {
            videoPage = videoRepository.findAll(pageable);
        }
        return videoPage;
    }

    @Override
    public Video onevideo(Integer videoId) {
        Video video = videoRepository.findOne(videoId);
        return video;
    }

    @Override
    public List<VideoItem> listByVid(Integer videoId) {
        List<VideoItem> videos = videoItemRepository.findAllByVideoId(videoId);
        return videos;
    }

    @Override
    public List<VideoCommentDto> commentByVid(Integer parentId) {
        Subject subject = SecurityUtils.getSubject();
        User userSession = (User) subject.getPrincipal();
        List<VideoComment> videoComment2 = videoCommentRepository.findByParentIdLike(parentId);
        List<VideoCommentDto> covert = new ArrayList<>();
        for (VideoComment videoComment : videoComment2) {
            VideoCommentDto videoCommentDto=new VideoCommentDto();
            User user = userRepository.findOne(videoComment.getCommentator());
            BeanUtils.copyProperties(videoComment,videoCommentDto);
            videoCommentDto.setUser(user);
            covert.add(videoCommentDto);
        }
        return covert;
    }

    @Override
    public VideoComment addVideoComment(Integer parentId, String vcContent) {
        Subject subject = SecurityUtils.getSubject();
        User user= (User)subject.getPrincipal();
        VideoComment videoComment = new VideoComment();
        videoComment.setParentId(parentId);
        videoComment.setVcContent(vcContent);
        videoComment.setCommentator(user.getUserId());
        VideoComment save = videoCommentRepository.save(videoComment);
        return save;
    }


}
