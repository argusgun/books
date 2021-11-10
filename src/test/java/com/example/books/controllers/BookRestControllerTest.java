package com.example.books.controllers;

import com.example.books.dto.BookDto;
import com.example.books.dto.CommentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private  final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getBooks() throws Exception {
        this.mockMvc.perform(get("/books"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(getBookDto1(),getBookDto2(),getBookDto3()))));
    }

    @Test
    void getCommentById() throws Exception{
        this.mockMvc.perform(get("/books/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void getBookByIdNonObject() throws Exception{
        this.mockMvc.perform(get("/books/5"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Non Object"));
    }

    @Test
    void putBook() throws Exception {
        BookDto bookDto = getBookDto2();
        bookDto.setId(null);
        bookDto.setComments(new ArrayList<>());
        this.mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("9"))
        ;
    }

    @Test
    void deleteBook() throws  Exception{
        this.mockMvc.perform(delete("/books/3")
        )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").value("Comment with id=3 is deleted!"))
        ;
    }

    @Test
    void updateBook() throws  Exception{
        BookDto bookDto2 = getBookDto2();
        bookDto2.setDescription("textmodified");
        this.mockMvc.perform(put("/books/2")
                .content(objectMapper.writeValueAsString(bookDto2))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.description").value("textmodified"))
        ;
        this.mockMvc.perform(get("/comments/"+bookDto2.getComments().get(0).getId()))
                .andExpect(status().is2xxSuccessful());
    }

    BookDto getBookDto1() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setCode("codebook1");
        bookDto.setName("book1");
        bookDto.setDescription("description1");
        bookDto.setAuthor("author1");
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(getCommentDto1());
        commentDtoList.add(getCommentDto5());
        bookDto.setComments(commentDtoList);
        return bookDto;
    }

    BookDto getBookDto2() {
        BookDto bookDto = new BookDto();
        bookDto.setId(2L);
        bookDto.setCode("codebook2");
        bookDto.setName("book2");
        bookDto.setDescription("description2");
        bookDto.setAuthor("author1");
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(getCommentDto2());
        bookDto.setComments(commentDtoList);
        return bookDto;
    }
    BookDto getBookDto3() {
        BookDto bookDto = new BookDto();
        bookDto.setId(3L);
        bookDto.setCode("codebook3");
        bookDto.setName("book3");
        bookDto.setDescription("description3");
        bookDto.setAuthor("author2");
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(getCommentDto3());
        commentDtoList.add(getCommentDto4());
        bookDto.setComments(commentDtoList);
        return bookDto;
    }

    CommentDto getCommentDto1(){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(4L);
        commentDto.setCode("codecomment1");
        commentDto.setRating(4.5f);
        commentDto.setText("text1");
        return commentDto;
    }

    CommentDto getCommentDto2(){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(5L);
        commentDto.setCode("codecomment2");
        commentDto.setRating(3.5f);
        commentDto.setText("text1");
        return commentDto;
    }

    CommentDto getCommentDto3(){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(6L);
        commentDto.setCode("codecomment3");
        commentDto.setRating(4.7f);
        commentDto.setText("text1");
        return commentDto;
    }

    CommentDto getCommentDto4(){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(7L);
        commentDto.setCode("codecomment4");
        commentDto.setRating(4.5f);
        commentDto.setText("text1");
        return commentDto;
    }

    CommentDto getCommentDto5(){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(8L);
        commentDto.setCode("codecomment5");
        commentDto.setRating(4.0f);
        commentDto.setText("text1");
        return commentDto;
    }
}