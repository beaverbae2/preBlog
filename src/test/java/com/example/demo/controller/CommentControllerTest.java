package com.example.demo.controller;

import com.example.demo.dto.Comment;
import com.example.demo.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@AutoConfigureMybatis
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService mockCommentService;

    @Autowired
    ObjectMapper objectMapper;

    private Comment comment1;

    @BeforeEach
    void setUp() {
        comment1 = Comment.builder()
                .id(1L)
                .content("방가방가")
                .uid(1L)
                .pid(1L)
                .status(false)
                .build();
    }

    @Test
    void write() throws Exception {
        // when
        when(mockCommentService.write(comment1)).thenReturn(1);

        String content = objectMapper.writeValueAsString(comment1);

        ResultActions actions = mockMvc.perform(post("/comment")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void show() throws Exception {

        //when
        when(mockCommentService.show(1L)).thenReturn(comment1);


        ResultActions actions = mockMvc.perform(get("/comment")
                .param("id", comment1.getId().toString()));


        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment1.getId()))
                .andExpect(jsonPath("$.uid").value(comment1.getUid()))
                .andExpect(jsonPath("$.pid").value(comment1.getPid()))
                .andExpect(jsonPath("$.content").value(comment1.getContent()))
                .andExpect(jsonPath("$.status").value(comment1.isStatus()));
    }

    @Test
    void showAll() throws Exception {
        List<Comment> commentList1 = new ArrayList<>();
        commentList1.add(comment1);
        commentList1.add(Comment.builder()
                .id(2L)
                .content("방가방가2")
                .uid(1L)
                .pid(1L)
                .status(false)
                .build());
        commentList1.add(Comment.builder()
                .id(3L)
                .content("방가방가3")
                .uid(2L)
                .pid(1L)
                .status(false)
                .build());

        when(mockCommentService.showAll()).thenReturn(commentList1);

        ResultActions actions = mockMvc.perform(get("/comments")
                .accept(MediaType.APPLICATION_JSON));

        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].content").value("방가방가"))
                .andExpect(jsonPath("$.[0].uid").value(1))
                .andExpect(jsonPath("$.[0].pid").value(1))
                .andExpect(jsonPath("$.[0].status").value(false))

                .andExpect(jsonPath("$.[1].content").value("방가방가2"))
                .andExpect(jsonPath("$.[1].uid").value(1))
                .andExpect(jsonPath("$.[1].pid").value(1))
                .andExpect(jsonPath("$.[1].status").value(false))

                .andExpect(jsonPath("$.[2].content").value("방가방가3"))
                .andExpect(jsonPath("$.[2].uid").value(2))
                .andExpect(jsonPath("$.[2].pid").value(1))
                .andExpect(jsonPath("$.[2].status").value(false));
    }

    @Test
    @DisplayName("내용을 수정한다")
    void update() throws Exception {
        Comment comment1 = Comment.builder()
                .id(1L)
                .content("방가방가")
                .uid(1L)
                .pid(1L)
                .status(true)
                .build();

        String content = objectMapper.writeValueAsString(comment1);

        when(mockCommentService.update(comment1)).thenReturn(1);
        when(mockCommentService.show(1L)).thenReturn(comment1);

        ResultActions actions1 = mockMvc.perform(put("/comment")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions2 = mockMvc.perform(get("/comment")
                .param("id", comment1.getId().toString()));


        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(comment1.getContent()))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    @DisplayName("글 삭제")
    void delete() throws Exception {
        Long id = 1L;

        when(mockCommentService.delete(id)).thenReturn(1);
        when(mockCommentService.show(id)).thenReturn(null);

        ResultActions actions1 = mockMvc.perform(MockMvcRequestBuilders.delete("/comment")
                .param("id", id.toString()));

        ResultActions actions2 = mockMvc.perform(get("/comment")
                .param("id", id.toString()));

        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect(content().string("")); // null
    }
}