package com.example.books.controllers;


import com.example.books.dto.BookDto;
import com.example.books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Object> getBooks(){
            if(bookService.getBooks().size()==0) return new ResponseEntity<>("Non books", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
        }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable("id") Long id){
        if(bookService.getBookById(id)==null){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(bookService.getBookById(id),HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity<Object> putBook(BookDto bookDto){
        return new ResponseEntity<>(bookService.putBook(bookDto),HttpStatus.CREATED );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable("id") Long id){
        String result = bookService.deleteBook(id);
        if(result.equals("error")){return new ResponseEntity<>("Non Object",HttpStatus.BAD_REQUEST);}
        return new ResponseEntity<>(result,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable("id") Long id,
                                                @RequestBody BookDto bookDto
    ) {
        return new ResponseEntity<>(bookService.updateBook(id,bookDto), HttpStatus.ACCEPTED);
    }
}
