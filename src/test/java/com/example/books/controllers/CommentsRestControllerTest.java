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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CommentsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private  final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getBooks() throws Exception {
        this.mockMvc.perform(get("/comments"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(getCommentDto1(),getCommentDto2(),getCommentDto3(),getCommentDto4(),getCommentDto5()))));
    }

    @Test
    void getCommentById() throws Exception{
        this.mockMvc.perform(get("/comments/5"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value("5"));
    }

    @Test
    void getCommentByIdNonObject() throws Exception{
        this.mockMvc.perform(get("/comments/10"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").value("Non Object"));
    }

    @Test
    void putComment() throws Exception {
        CommentDto commentDto = getCommentDto2();
        commentDto.setId(null);
        this.mockMvc.perform(post("/comments")
                .content(objectMapper.writeValueAsString(Arrays.asList(commentDto,getBookDto3())))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("9"))
        ;
    }

    @Test
    void deleteComment() throws  Exception{
        this.mockMvc.perform(delete("/comments/6")
                .content(objectMapper.writeValueAsString(getCommentDto3()))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value("6"))
        ;
    }

    @Test
    void updateComment() throws  Exception{
        CommentDto commentDto4 = getCommentDto4();
        commentDto4.setText("textmodified");
        this.mockMvc.perform(put("/comments/7")
                .content(objectMapper.writeValueAsString(commentDto4))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.text").value("textmodified"))
        ;
        this.mockMvc.perform(get("/books/3"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(getBookDto3())));
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
        CommentDto commentDto4 = getCommentDto4();
        commentDto4.setText("textmodified");
        commentDtoList.add(commentDto4);
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