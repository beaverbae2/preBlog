package com.example.demo.dao;

import com.example.demo.dao.impl.PostDaoImpl;
import com.example.demo.dto.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest(excludeFilters = {})
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
        .shows(1)
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

        List<Post> postList2 = postDao.showAll();

        for (int i = 0; i < postList1.size(); i++) {
            Post post1 = postList1.get(i);
            Post post2 = postList2.get(i);

            assertThat(post1.getTitle()).isEqualTo(post2.getTitle());
            assertThat(post1.getContent()).isEqualTo(post2.getContent());
            assertThat(post1.getUid()).isEqualTo(post2.getUid());
            assertThat(post1.getShows()).isEqualTo(post2.getShows());
            assertThat(post1.isStatus()).isEqualTo(post2.isStatus());
        }
    }

    @Test
    @DisplayName("제목과 내용을 수정한다")
    void update() {
        Post post1 = Post.builder()
                .id(1L)
                .title("하이하이")
                .content("하이루하이루")
                .uid(1L)
                .shows(1)
                .status(true)
                .build();

        int count = postDao.update(post1);
        Post post2 = postDao.show(1L);

        assertThat(count).isEqualTo(1);
        assertThat(post2.getId()).isEqualTo(1L);
        assertThat(post2.getTitle()).isEqualTo("하이하이");
        assertThat(post2.getContent()).isEqualTo("하이루하이루");
        assertThat(post2.getUid()).isEqualTo(1L);
        assertThat(post2.getShows()).isEqualTo(1);
        assertThat(post2.isStatus()).isEqualTo(true);
    }

    @Test
    @DisplayName("굴 조회시 조회수 증가")
    void updateShows() {
        Post post1 = Post.builder()
                .id(1L)
                .shows(1)
                .build();

        int count = postDao.updateShows(post1.getId());
        Post post2 = postDao.show(post1.getId());

        assertThat(count).isEqualTo(1);
        assertThat(post2.getShows()).isEqualTo((post1.getShows()+1));
    }

    @Test
    @DisplayName("글 삭제")
    void delete() {
        Long id = 1L;
        assertThat(postDao.delete(id)).isEqualTo(1);
        assertThat(postDao.show(id)).isEqualTo(null);
    }
}
