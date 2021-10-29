package com.example.books.controllers;


import com.example.books.dto.BookDto;
import com.example.books.entities.BookEntity;
import com.example.books.repos.BookRepo;
import com.example.books.services.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookRepo bookRepo;
    private final ConverterService converterService;

    @GetMapping
    public ResponseEntity<Object> getBooks(){
            if(bookRepo.findAll().size()==0) return new ResponseEntity<>("Non books", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(bookRepo.findAll().stream().map( bookEntity -> converterService.getBookDtoFromBookEntity(bookEntity)).collect(Collectors.toList()), HttpStatus.OK);
        }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable("id") Long id){
        if(bookRepo.findById(id).equals(Optional.empty())){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(converterService.getBookDtoFromBookEntity(bookRepo.getById(id)),HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<Object> putComment(BookDto bookDto){
        BookEntity save = bookRepo.save(converterService.getBookEntityFromBookDto(bookDto));
        return new ResponseEntity<>(converterService.getBookDtoFromBookEntity(save),HttpStatus.CREATED );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("id") Long id){
        if(bookRepo.findById(id).equals(Optional.empty())){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(converterService.getBookDtoFromBookEntity(bookRepo.getById(id)),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable("id") Long id,
                                                @RequestBody BookDto bookDto
    ) {
        BookEntity save = bookRepo.save(converterService.getBookEntityFromBookDto(bookDto));
        return new ResponseEntity<>(converterService.getBookDtoFromBookEntity(save), HttpStatus.ACCEPTED);
    }
}
