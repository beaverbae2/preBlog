package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.User;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserService userService;

    @Mock
    private UserDao userDao;

    private User user1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userDao);

        user1 = User.builder()
                .uid("hello")
                .nickname("kim3")
                .password("1234")
                .status(true)
                .build();
    }


    @Test
    void 아이디_비밀번호_닉네임을_받아_회원가입을_한다() throws Exception {
        // stub
        when(userDao.save(user1)).thenReturn(1);

        int count = userService.save(user1);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void 중복된_아이디_인지_확인한다() {
        // given
        User user2 = User.builder()
                .uid("hello")
                .nickname("Bae3")
                .password("1234")
                .status(true)
                .build();

        // when
        when(userDao.save(user1)).thenReturn(1);
        when(userDao.findByUid(user2.getUid())).thenReturn(user1);


        // then
        assertThat(userService.save(user1)).isEqualTo(1);
        assertThat(userService.findByUid(user2.getUid())).isEqualTo(user1);
    }

    @Test
    void 회원_탈퇴를_한다() {
        // when
        when(userDao.save(user1)).thenReturn(1);
        when(userDao.delete(user1.getUid())).thenReturn(1);
        when(userDao.findByUid(user1.getUid())).thenReturn(user1);
        user1.setStatus(false);

        // then
        assertThat(userService.save(user1)).isEqualTo(1);
        assertThat(userService.delete(user1.getUid())).isEqualTo(1);
        assertThat(userService.findByUid(user1.getUid()).isStatus()).isEqualTo(false);
    }
}