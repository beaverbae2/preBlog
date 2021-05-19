package com.example.demo.controller;

import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import javax.xml.transform.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMybatis
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @Autowired
    ObjectMapper objectMapper;

    private User user1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .uid("hello")
                .nickname("kim3")
                .password("1234")
                .status(true)
                .build();
    }

    @Test
    void join() throws Exception {
        // when
        when(mockUserService.save(user1)).thenReturn(1);

        String content = objectMapper.writeValueAsString(user1);

        ResultActions actions = mockMvc.perform(post("/user")
                                    .content(content)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void 중복된_아이디_인지_확인한다() throws Exception {
        // given
        User user2 = User.builder()
                .uid("hello")
                .nickname("Bae3")
                .password("1234")
                .status(true)
                .build();

        String user1JsonString = objectMapper.writeValueAsString(user1);

        // when
        when(mockUserService.save(user1)).thenReturn(1);
        when(mockUserService.findByUid(user2.getUid())).thenReturn(user1);

        ResultActions actions1 = mockMvc.perform(post("/user")
                .content(user1JsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions2 = mockMvc.perform(get("/user")
                .param("uid", user2.getUid()));

        // then
        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value(user1.getUid()));
    }

    @Test
    void 회원_탈퇴를_한다() throws Exception {
        // when
        when(mockUserService.save(user1)).thenReturn(1);
        when(mockUserService.delete(user1.getUid())).thenReturn(1);
        user1.setStatus(false);
        when(mockUserService.findByUid(user1.getUid())).thenReturn(user1);

        String user1JsonString = objectMapper.writeValueAsString(user1);


        ResultActions actions1 = mockMvc.perform(post("/user")
                .content(user1JsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions2 = mockMvc.perform(put("/user")
                .param("uid", user1.getUid()));

        ResultActions actions3 = mockMvc.perform(get("/user")
                .param("uid", user1.getUid()));

        // then
        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions3.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value(user1.getUid()))
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void 회원_수정을_한다() throws Exception {
        User user1 = User.builder()
                .id(2L)
                .uid("hello")
                .nickname("kim")
                .password("1234")
                .status(true)
                .build();

        String content = objectMapper.writeValueAsString(user1);

        when(mockUserService.update(user1)).thenReturn(1);
        when(mockUserService.findByUid("hello")).thenReturn(user1);

        ResultActions actions1 = mockMvc.perform(put("/user/renewal")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        ResultActions actions2 = mockMvc.perform((get("/user")
                .param("uid", user1.getUid().toString())));

        actions1.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        actions2.andDo(print())
                .andExpect((status().isOk()))
                .andExpect(jsonPath("$.uid").value(user1.getUid()))
                .andExpect(jsonPath("$.nickname").value(user1.getNickname()))
                .andExpect(jsonPath("$.password").value(user1.getPassword()));
    }
}