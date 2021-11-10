package com.example.books.services;

import com.example.books.dto.CommentDto;
import com.example.books.entities.CommentEntity;
import com.example.books.mappers.CommentMapper;
import com.example.books.repos.BookRepo;
import com.example.books.repos.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private  final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private  final BookRepo bookRepo;

    public CommentDto getCommentById(long id) {
        if(commentRepo.findById(id).equals(Optional.empty())) return null;
        return commentMapper.toDto(commentRepo.getById(id));
    }

    public List<CommentDto> getComments() {
        return commentRepo.findAll().stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    public String deleteComment(long id) {
        if (commentRepo.findById(id).equals(Optional.empty())) return "error";
        commentRepo.deleteById(id);
        return "Comment with id=" + id + " is deleted!";
    }

    public CommentDto updateComment(long id, CommentDto commentDto) {
        CommentEntity commentEntityByDb = commentRepo.getById(id);
        CommentEntity commentEntity = commentMapper.toEntity(commentDto);
        BeanUtils.copyProperties(commentEntity, commentEntityByDb,"book");
        commentRepo.save(commentEntityByDb);
        return commentMapper.toDto(commentEntityByDb);
    }

    public CommentDto putComment(CommentDto commentDto, long id) {
        CommentEntity commentEntity = commentMapper.toEntity(commentDto);
        System.out.println(commentEntity);
        commentEntity.setBook(bookRepo.getById(id));
        commentRepo.save(commentEntity);
        return commentMapper.toDto(commentEntity);
    }

}
