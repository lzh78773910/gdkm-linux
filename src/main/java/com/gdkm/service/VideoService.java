package com.gdkm.service;

import com.gdkm.dto.VideoDto;
import com.gdkm.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoService {

    Page<VideoDto> list(Pageable pageable, String videoTitle);

    void add(Video video);

    void delete(Integer videoId);

    Video update(Video video);

    VideoDto one(Integer videoId);

    Video onevideo(Integer videoId);
}
