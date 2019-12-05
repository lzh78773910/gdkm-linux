package com.gdkm.converter;

import com.gdkm.dto.VideoDto;
import com.gdkm.model.Video;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class VideoTOVideoDtoConverter {

    public static VideoDto convert(Video video) {

        VideoDto videoDto = new VideoDto();
        BeanUtils.copyProperties(video, videoDto);
        return videoDto;
    }

    public static List<VideoDto> convert(List<Video> videoList) {
        return videoList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
