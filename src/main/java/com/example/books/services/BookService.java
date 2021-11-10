package com.example.books.services;

import com.example.books.dto.BookDto;
import com.example.books.entities.BookEntity;
import com.example.books.mappers.BookMapper;
import com.example.books.repos.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;

    public BookDto getBookById(long id) {
        if(bookRepo.findById(id).equals(Optional.empty())) return null;
        return bookMapper.toDto(bookRepo.getById(id));
    }

    public List<BookDto> getBooks() {
        return bookRepo.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public String deleteBook(long id) {
        if (bookRepo.findById(id).equals(Optional.empty())) return "error";
        bookRepo.deleteById(id);
        return "Comment with id=" + id + " is deleted!";
    }

    public BookDto updateBook(long id, BookDto bookDto) {
        BookEntity bookEntityByDb = bookRepo.getById(id);
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        BeanUtils.copyProperties(bookEntity, bookEntityByDb);
        bookRepo.save(bookEntityByDb);
        return bookMapper.toDto(bookEntityByDb);
    }

    public BookDto putBook(BookDto bookDto) {
        BookEntity save = bookRepo.save(bookMapper.toEntity(bookDto));
        return bookMapper.toDto(save);
    }
}
