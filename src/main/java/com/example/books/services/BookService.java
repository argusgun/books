package com.example.books.services;

import com.example.books.dto.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    boolean findBook(long id);

    BookDto getBookById(long id);

    List<BookDto> getBooks();

    Long deleteBook(long id);

    BookDto updateBook(BookDto bookDto);

    BookDto putBook(BookDto bookDto);
}
