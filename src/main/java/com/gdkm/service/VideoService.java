package com.gdkm.service;

import com.gdkm.dto.VideoCommentDto;
import com.gdkm.dto.VideoDto;
import com.gdkm.model.Video;
import com.gdkm.model.VideoComment;
import com.gdkm.model.VideoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    Page<VideoDto> list(Pageable pageable, String videoTitle);

    void add(Video video);

    void delete(Integer videoId);

    Video update(Video video);

    VideoDto one(Integer videoId);

    Video onevideo(Integer videoId);

    VideoItem Item(Integer viId);

    void additem(Integer videoId,String title, MultipartFile file) throws IOException;

    Page<Video> getPage(Integer page, Integer size,String title,String sortStr);

    List<VideoItem> listByVid(Integer videoId);

    List<VideoCommentDto> commentByVid(Integer parentId);

    VideoComment addVideoComment(Integer parentId, String vcContent);
}
