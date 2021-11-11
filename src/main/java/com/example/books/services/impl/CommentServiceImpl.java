package com.example.books.services.impl;

import com.example.books.dto.CommentDto;
import com.example.books.entities.CommentEntity;
import com.example.books.mappers.CommentMapper;
import com.example.books.repos.BookRepo;
import com.example.books.repos.CommentRepo;
import com.example.books.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private  final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private  final BookRepo bookRepo;

    public  boolean findComment(long id){
        if(commentRepo.findById(id).isEmpty()) return false;
        return true;
    }

    public CommentDto getCommentById(long id) {
        return commentMapper.toDto(commentRepo.getById(id));
    }

    public List<CommentDto> getComments() {
        return commentRepo.findAll().stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    public Long deleteComment(long id) {
        commentRepo.deleteById(id);
        return id;
    }

    public CommentDto updateComment(CommentDto commentDto) {
        CommentEntity save = commentRepo.save(commentMapper.toEntity(commentDto));
        return commentMapper.toDto(save);
    }

    public CommentDto putComment(CommentDto commentDto, long id) {
        CommentEntity commentEntity = commentMapper.toEntity(commentDto);
        commentEntity.setBook(bookRepo.getById(id));
        commentRepo.save(commentEntity);
        return commentMapper.toDto(commentEntity);
    }
}
