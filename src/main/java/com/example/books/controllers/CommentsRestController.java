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
    public ResponseEntity<Object> getCommentById(@PathVariable("id") Long id){
        if(commentService.getCommentById(id)==null){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(commentService.getCommentById(id),HttpStatus.OK );
    }

    @PostMapping("/books/{id}")
    public ResponseEntity<Object> putComment(@RequestBody CommentDto commentDto, @PathVariable("id") long id){
        return new ResponseEntity<>(commentService.putComment(commentDto, id),HttpStatus.CREATED );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id){
        if(commentService.getCommentById(id).equals("error")){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(commentService.deleteComment(id),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable("id") Long id,
                                                @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(commentService.updateComment(id,commentDto), HttpStatus.ACCEPTED);
    }


}
