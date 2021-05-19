package com.example.demo.dao;

import com.example.demo.dao.impl.UserDaoImpl;
import com.example.demo.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 DB(Replace.ANY)가 아닌 실제 DB 사용
@Import(UserDaoImpl.class) // 구체화된 클래스를 가져와야함
class UserDaoTest {
    @Autowired
    private UserDao userDao;

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
        int count = userDao.save(user1);
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
        int userCount = userDao.save(user1);
        User dupUser = userDao.findByUid(user2.getUid());

        // then
        assertThat(userCount).isEqualTo(1);
        assertThat(dupUser).isNotNull();
    }

    @Test
    void 회원_탈퇴를_한다() {
        // when
        userDao.save(user1);
        userDao.delete(user1.getUid());
        boolean status =  userDao.findByUid(user1.getUid()).isStatus();

        // then
        assertThat(status).isEqualTo(false);
    }

    @Test
    void 회원_수정을_한다(){
        User user1 = User.builder()
                .id(2L)
                .uid("hello")
                .nickname("kim")
                .password("1234")
                .status(true)
                .build();

        int count = userDao.update(user1);
        User user2 = userDao.findByUid("hello");

        assertThat(count).isEqualTo(1);
        assertThat(user2.getId()).isEqualTo(2L);
        assertThat(user2.getUid()).isEqualTo("hello");
        assertThat(user2.getPassword()).isEqualTo("1234");
        assertThat(user2.getNickname()).isEqualTo("kim");
        assertThat(user2.isStatus()).isEqualTo(true);
    }
}
