package com.example.books.controllers;


import com.example.books.dto.BookDto;
import com.example.books.dto.CommentDto;
import com.example.books.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsRestController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(){
            return new ResponseEntity<>(commentService.getComments(),
                    HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id){
        if(!commentService.findComment(id)){return new ResponseEntity<>(new CommentDto(),HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(commentService.getCommentById(id),HttpStatus.OK );
    }

    @PostMapping("/books/{id}")
    public ResponseEntity<Object> putComment(@RequestBody CommentDto commentDto, @PathVariable("id") long id){
        return new ResponseEntity<>(commentService.putComment(commentDto, id),HttpStatus.CREATED );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteComment(@PathVariable("id") Long id){
        if(!commentService.findComment(id)){return new ResponseEntity<>(-1L,HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(commentService.deleteComment(id),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(commentDto), HttpStatus.ACCEPTED);
    }


}
