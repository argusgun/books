package com.example.books.mappers;

import com.example.books.dto.BookDto;
import com.example.books.entities.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(BookEntity bookEntity);

    BookEntity toEntity(BookDto bookDto);
}
