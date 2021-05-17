package com.example.demo.api;

import com.example.demo.dto.Post;
import com.example.demo.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // (webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class PostAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void write() throws Exception {
        Post post1 =  Post.builder()
                .id(1L)
                .title("하이")
                .content("하이루")
                .uid(1L)
                .shows(1)
                .status(true)
                .build();

        String content = objectMapper.writeValueAsString(post1);
        ResultActions actions = mockMvc.perform(post("/post")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());

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

        ResultActions actions = mockMvc.perform(get("/post")
            .param("id",post1.getId().toString()));

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
