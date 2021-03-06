package com.example.books.controllers;


import com.example.books.dto.CommentDto;
import com.example.books.entities.CommentEntity;
import com.example.books.repos.CommentRepo;
import com.example.books.services.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsRestController {
    private final CommentRepo commentRepo;
    private final ConverterService converterService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(){
            return new ResponseEntity<>(commentRepo.findAll().stream()
                    .map(c -> converterService.getCommentDtoFromCommentEntity(c))
                    .collect(Collectors.toList()),
                    HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable("id") Long id){
        if(commentRepo.findById(id).equals(Optional.empty())){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(converterService.getCommentDtoFromCommentEntity(commentRepo.getById(id)),HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<Object> putComment(CommentDto commentDto){
        CommentEntity commentEntity = commentRepo.save(converterService.getCommentEntityFromCommentDto(commentDto));
        return new ResponseEntity<>(converterService.getCommentDtoFromCommentEntity(commentEntity),HttpStatus.CREATED );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("id") Long id){
        if(commentRepo.findById(id).equals(Optional.empty())){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(converterService.getCommentDtoFromCommentEntity(commentRepo.getById(id)),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable("id") Long id,
                                                @RequestBody CommentDto commentDto
    ) {
        commentDto.setId(id);
        CommentEntity commentEntity = commentRepo.save(converterService.getCommentEntityFromCommentDto(commentDto));
        return new ResponseEntity<>(converterService.getCommentDtoFromCommentEntity(commentEntity), HttpStatus.ACCEPTED);
    }


}
