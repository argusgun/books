package com.example.books.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String author;
    private List<CommentDto> comments;

}
