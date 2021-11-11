package com.example.books.controllers;


import com.example.books.dto.BookDto;
import com.example.books.dto.CommentDto;
import com.example.books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks(@RequestParam(value = "rating",defaultValue="-1") Double rating,
                                                  @RequestParam(value = "author",defaultValue = "") String author){
        List<BookDto> books = bookService.getBooks();
        if(books.isEmpty()) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            if(rating==-1 && author.equals(""))return new ResponseEntity<>(books, HttpStatus.OK);
            else
                if(!author.equals("") && rating==-1) return new ResponseEntity<>(books.stream()
                        .filter(bookDto -> author.equals(bookDto.getAuthor()))
                        .collect(Collectors.toList()), HttpStatus.OK);
                    else if(rating!=-1 && author.equals("")) return new ResponseEntity<>(books.stream()
                    .filter(bookDto -> rating.compareTo(bookDto.getComments().stream()
                            .mapToDouble(CommentDto::getRating).sum())<=0)
            .collect(Collectors.toList()), HttpStatus.OK);
                    else return new ResponseEntity<>(books.stream()
                            .filter(bookDto -> rating.compareTo(bookDto.getComments().stream()
                                    .mapToDouble(CommentDto::getRating).sum())<=0 && author.equals(bookDto.getAuthor()))
                            .collect(Collectors.toList()), HttpStatus.OK);
        }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id){
        if(!bookService.findBook(id)){return new ResponseEntity<>(new BookDto(),HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(bookService.getBookById(id),HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<BookDto> putBook(BookDto bookDto){
        return new ResponseEntity<>(bookService.putBook(bookDto),HttpStatus.CREATED );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable("id") Long id){
        if(!bookService.findBook(id)){return new ResponseEntity<>(-1L,HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(bookService.deleteBook(id),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.updateBook(bookDto), HttpStatus.ACCEPTED);
    }

}
