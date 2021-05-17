package com.example.demo.dao;

import com.example.demo.dao.impl.PostDaoImpl;
import com.example.demo.dto.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;


import static org.assertj.core.api.Assertions.assertThat;
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 DB(Replace.ANY)가 아닌 실제 DB 사용
@Import(PostDaoImpl.class) // 구체화된 클래스를 가져와야함
class PostDaoTest {
    @Autowired
    private PostDao postDao;

    private Post post1;

    @BeforeEach
    void setUp() {
        post1 = Post.builder()
        .title("안녕하세요")
        .content("하이루")
        .uid(1L)
        .shows(0)
        .build();
    }
    @Test
    void write() throws Exception {
        int count = postDao.write(post1);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void show() throws Exception {
        Post post = postDao.show(1L);
        assertThat(post.getId()).isEqualTo(1);
        assertThat(post.getUid()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("하이");
        assertThat(post.getContent()).isEqualTo("하이루");
        assertThat(post.getShows()).isEqualTo(1);
        assertThat(post.isStatus()).isEqualTo(true);
    }






}
