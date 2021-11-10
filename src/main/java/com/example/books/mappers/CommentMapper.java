package com.example.books.mappers;

import com.example.books.dto.CommentDto;
import com.example.books.entities.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(CommentEntity commentEntity);
    @Mapping(target = "book", ignore = true)
    CommentEntity toEntity(CommentDto commentDto);
}
