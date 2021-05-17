package com.example.demo.controller;

import com.example.demo.dto.Post;
import com.example.demo.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMybatis
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService mockPostService;

    @Autowired
    ObjectMapper objectMapper;

    private Post post1;

    @BeforeEach
    void setUp() {
        post1 = Post.builder()
                .title("안녕하세요")
                .content("하이루")
                .uid(1L)
                .shows(0)
                .status(true)
                .build();
    }

    @Test
    void write() throws Exception {
        // when
        when(mockPostService.write(post1)).thenReturn(1);

        String content = objectMapper.writeValueAsString(post1);

        ResultActions actions = mockMvc.perform(post("/post")
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
        Post post1 =  Post.builder()
                .id(1L)
                .title("하이")
                .content("하이루")
                .uid(1L)
                .shows(1)
                .status(true)
                .build();
        //when
        when(mockPostService.show(1L)).thenReturn(post1);


        ResultActions actions = mockMvc.perform(get("/post")
                .param("id", post1.getId().toString()));


        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post1.getId()))
                .andExpect(jsonPath("$.uid").value(post1.getUid()))
                .andExpect(jsonPath("$.title").value(post1.getTitle()))
                .andExpect(jsonPath("$.content").value(post1.getContent()))
                .andExpect(jsonPath("$.shows").value(post1.getShows()))
                .andExpect(jsonPath("$.status").value(post1.isStatus()));
    }

}