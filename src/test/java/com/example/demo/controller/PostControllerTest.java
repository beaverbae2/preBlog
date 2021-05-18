package com.example.demo.controller;

import com.example.demo.dto.Post;
import com.example.demo.service.PostService;
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

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void showAll() throws Exception {
        List<Post> postList1 = new ArrayList<>();
        postList1.add(Post.builder()
                .title("하이")
                .content("하이루")
                .uid(1L)
                .shows(1)
                .status(true)
                .build());
        postList1.add(Post.builder()
                .title("바이")
                .content("바이루")
                .uid(1L)
                .shows(0)
                .status(false)
                .build());
        postList1.add(Post.builder()
                .title("안녕안녕")
                .content("안녕하십니다")
                .uid(1L)
                .shows(0)
                .status(false)
                .build());

        when(mockPostService.showAll()).thenReturn(postList1);

        ResultActions actions = mockMvc.perform(get("/posts")
                .accept(MediaType.APPLICATION_JSON));

        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("하이"))
                .andExpect(jsonPath("$.[0].content").value("하이루"))
                .andExpect(jsonPath("$.[0].uid").value(1))
                .andExpect(jsonPath("$.[0].shows").value(1))
                .andExpect(jsonPath("$.[0].status").value(true))

                .andExpect(jsonPath("$.[1].title").value("바이"))
                .andExpect(jsonPath("$.[1].content").value("바이루"))
                .andExpect(jsonPath("$.[1].uid").value(1))
                .andExpect(jsonPath("$.[1].shows").value(0))
                .andExpect(jsonPath("$.[1].status").value(false))

                .andExpect(jsonPath("$.[2].title").value("안녕안녕"))
                .andExpect(jsonPath("$.[2].content").value("안녕하십니다"))
                .andExpect(jsonPath("$.[2].uid").value(1))
                .andExpect(jsonPath("$.[2].shows").value(0))
                .andExpect(jsonPath("$.[2].status").value(false));
    }

    @Test
    @DisplayName("제목과 내용을 수정한다")
    void update() throws Exception {
        Post post1 = Post.builder()
                .id(1L)
                .title("하이하이")
                .content("하이루하이루")
                .uid(1L)
                .shows(1)
                .status(true)
                .build();

        String content = objectMapper.writeValueAsString(post1);

        when(mockPostService.update(post1)).thenReturn(1);
        when(mockPostService.show(1L)).thenReturn(post1);

        ResultActions actions1 = mockMvc.perform(put("/post")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions2 = mockMvc.perform(get("/post")
                .param("id", post1.getId().toString()));


        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(post1.getTitle()))
                .andExpect(jsonPath("$.content").value(post1.getContent()))
                .andExpect(jsonPath("$.status").value(post1.isStatus()));
    }

    @Test
    @DisplayName("굴 조회시 조회수 증가")
    void updateShows() throws Exception {
        Post post1 = Post.builder()
                .id(1L)
                .shows(1+1)
                .build();

        when(mockPostService.updateShows(post1.getId())).thenReturn(1);
        when(mockPostService.show(post1.getId())).thenReturn(post1);

        ResultActions actions1 = mockMvc.perform(put("/post/shows")
                .param("id", post1.getId().toString()));

        ResultActions actions2 = mockMvc.perform(get("/post")
                .param("id", post1.getId().toString())
                .accept(MediaType.APPLICATION_JSON));


        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shows").value(2));
    }

    @Test
    @DisplayName("글 삭제")
    void delete() throws Exception {
        Long id = 1L;

        when(mockPostService.delete(id)).thenReturn(1);
        when(mockPostService.show(id)).thenReturn(null);

        ResultActions actions1 = mockMvc.perform(MockMvcRequestBuilders.delete("/post")
                .param("id", id.toString()));

        ResultActions actions2 = mockMvc.perform(get("/post")
                .param("id", id.toString()));

        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect(content().string("")); // null
    }
}