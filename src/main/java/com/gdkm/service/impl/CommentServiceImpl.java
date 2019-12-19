package com.gdkm.service.impl;

import com.gdkm.Repository.CommentRepository;
import com.gdkm.Repository.UserRepository;
import com.gdkm.converter.CommentToCommentDtoConverter;
import com.gdkm.dto.CommentDto;
import com.gdkm.model.Comment;
import com.gdkm.model.User;
import com.gdkm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> listByQid(Integer parentId) {
//        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        List<Comment> comment = commentRepository.findByParentIdLike(parentId);
//        List<Comment> comment = commentRepository.findByParentIdLike(parentId,sort);
        List<CommentDto> commentDtos = CommentToCommentDtoConverter.convert(comment);
        for (CommentDto commentDto:commentDtos){
            User user = userRepository.findOne(commentDto.getCommentator());
            commentDto.setUser(user);
        }
        return commentDtos;
    }

    @Override
    public Comment addCommentByUser(Comment comment) {
        return commentRepository.save(comment);
    }

}
