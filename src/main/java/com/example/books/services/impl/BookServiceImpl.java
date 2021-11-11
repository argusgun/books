package com.example.books.services.impl;

import com.example.books.dto.BookDto;
import com.example.books.entities.BookEntity;
import com.example.books.mappers.BookMapper;
import com.example.books.repos.BookRepo;
import com.example.books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;

    public boolean findBook(long id){
        if (bookRepo.findById(id).isEmpty()) return false;
        return true;
    }

    public BookDto getBookById(long id) {
        return bookMapper.toDto(bookRepo.getById(id));
    }

    public List<BookDto> getBooks() {
        return bookRepo.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public Long deleteBook(long id) {
        bookRepo.deleteById(id);
        return id;
    }

    public BookDto updateBook(BookDto bookDto) {
        BookEntity save = bookRepo.save(bookMapper.toEntity(bookDto));
        return bookMapper.toDto(save);
    }

    public BookDto putBook(BookDto bookDto) {
        BookEntity save = bookRepo.save(bookMapper.toEntity(bookDto));
        return bookMapper.toDto(save);
    }
}
