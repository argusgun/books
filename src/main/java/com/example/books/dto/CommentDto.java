package com.example.books.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String code;
    private String text;
    private double rating;
}
