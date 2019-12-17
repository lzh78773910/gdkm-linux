package com.gdkm.converter;

import com.gdkm.dto.CommentDto;
import com.gdkm.model.Comment;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CommentToCommentDtoConverter {
    public static CommentDto convert(Comment comment) {
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);
        return commentDto;
    }

    public static List<CommentDto> convert(List<Comment> comments) {
        return comments.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

}
