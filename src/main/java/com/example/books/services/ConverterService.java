package com.example.books.services;

import com.example.books.dto.BookDto;
import com.example.books.dto.CommentDto;
import com.example.books.entities.BookEntity;
import com.example.books.entities.CommentEntity;
import com.example.books.repos.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConverterService {

    private final CommentRepo commentRepo;
    public BookDto getBookDtoFromBookEntity(BookEntity bookEntity) {
        BookDto bookDto = new BookDto();
        bookDto.setId(bookEntity.getId());
        bookDto.setCode(bookEntity.getCode());
        bookDto.setComments(bookEntity.getComments().stream().map(p -> getCommentDtoFromCommentEntity(p)).collect(Collectors.toList()));
        bookDto.setDescription(bookEntity.getDescription());
        bookDto.setName(bookEntity.getName());
        bookDto.setAuthor(bookEntity.getAuthor());
        return bookDto;
    }

    public CommentDto getCommentDtoFromCommentEntity(CommentEntity commentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setCode(commentEntity.getCode());
        commentDto.setRating(commentEntity.getRating());
        commentDto.setText(commentEntity.getText());
        return commentDto;
    }

    public BookEntity getBookEntityFromBookDto(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        if (bookDto.getId() != null) bookEntity.setId(bookDto.getId());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setCode(bookDto.getCode());
        bookEntity.setName(bookDto.getName());
        if(bookDto.getComments()!= null)
            bookEntity.setComments(bookDto.getComments().stream().map(this::getCommentEntityFromCommentDto).collect(Collectors.toList()));
        else{
            bookEntity.setComments(new ArrayList<>());
        }
        bookEntity.setDescription(bookDto.getDescription());
        return bookEntity;
    }

    public CommentEntity getCommentEntityFromCommentDto(CommentDto commentDto) {
        CommentEntity commentEntity = new CommentEntity();
        if (commentDto.getId() != null)
            commentEntity.setId(commentDto.getId());
        commentEntity.setRating(commentDto.getRating());
        commentEntity.setCode(commentDto.getCode());
        commentEntity.setText(commentDto.getText());
        return commentEntity;
    }
}
