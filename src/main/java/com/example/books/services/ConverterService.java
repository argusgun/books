package com.example.books.services;

import com.example.books.dto.BookDto;
import com.example.books.dto.CommentDto;
import com.example.books.entities.BookEntity;
import com.example.books.entities.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ConverterService {
    public BookDto getBookDtoFromBookEntity(BookEntity bookEntity){
        BookDto bookDto = new BookDto();
        bookDto.setId(bookEntity.getId());
        bookDto.setCode(bookEntity.getCode());
        bookDto.setComments(bookEntity.getComments().stream().map(p -> getCommentDtoFromCommentEntity(p)).collect(Collectors.toSet()));
        bookDto.setDescription(bookEntity.getDescription());
        bookDto.setName(bookEntity.getName());
        bookDto.setAuthor(bookEntity.getAuthor());
        return bookDto;
    }

    public CommentDto getCommentDtoFromCommentEntity(CommentEntity commentEntity){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setCode(commentEntity.getCode());
        commentDto.setRating(commentEntity.getRating());
        commentDto.setText(commentEntity.getText());
        return commentDto;
    }

    public BookEntity getBookEntityFromBookDto(BookDto bookDto){
        BookEntity bookEntity = new BookEntity();
        if(bookDto.getId()!=null || bookDto.getId().equals(null)) bookEntity.setId(bookDto.getId());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setCode(bookDto.getCode());
        bookEntity.setName(bookDto.getName());
        bookEntity.setComments(bookDto.getComments().stream().map(c -> getCommentEntityFromCommentDto(c)).collect(Collectors.toList()));
        bookEntity.setDescription(bookDto.getDescription());
        return bookEntity;
    }

    public CommentEntity getCommentEntityFromCommentDto(CommentDto commentDto){
        CommentEntity commentEntity = new CommentEntity();
        if(commentDto.getId()!=null || commentDto.getId().equals(null)) commentEntity.setId(commentDto.getId());
        commentEntity.setRating(commentDto.getRating());
        commentEntity.setCode(commentEntity.getCode());
        commentEntity.setText(commentEntity.getText());
        return commentEntity;
    }
}
