package com.gdkm.converter;

import com.gdkm.dto.VideoCommentDto;
import com.gdkm.model.VideoComment;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class VCommentToVCommentDtoConverter {

    public static VideoCommentDto convert(VideoComment videoComment) {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        BeanUtils.copyProperties(videoComment, videoCommentDto);
        return videoCommentDto;
    }

    public static List<VideoCommentDto> convert(List<VideoComment> videoComments) {
        return videoComments.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
