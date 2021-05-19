package com.example.demo.api;

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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // (webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void sign() throws Exception {
        String content = objectMapper.writeValueAsString(user1);
        System.out.println(content);
        ResultActions actions = mockMvc.perform(post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
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
