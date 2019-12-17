package com.gdkm.service;

import com.gdkm.dto.CommentDto;
import com.gdkm.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);

    List<CommentDto> listByQid(Integer parentId);

    Comment addCommentByUser(Comment comment);
}
