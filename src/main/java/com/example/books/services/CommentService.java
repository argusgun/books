package com.example.books.services;

import com.example.books.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    boolean findComment(long id);

    CommentDto getCommentById(long id);

    List<CommentDto> getComments();

    Long deleteComment(long id);

    CommentDto updateComment(CommentDto commentDto);

    CommentDto putComment(CommentDto commentDto, long id);

}
