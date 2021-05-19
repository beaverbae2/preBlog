package com.example.demo.dao;


import com.example.demo.dao.impl.CommentDaoImpl;
import com.example.demo.dto.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest(excludeFilters = {})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 DB(Replace.ANY)가 아닌 실제 DB 사용
@Import(CommentDaoImpl.class) // 구체화된 클래스를 가져와야함
class CommentDaoTest {
    @Autowired
    private CommentDao commentDao;

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
        int count = commentDao.write(comment1);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void show() throws Exception {
        Comment comment = commentDao.show(1L);
        assertThat(comment.getId()).isEqualTo(1);
        assertThat(comment.getContent()).isEqualTo("방가방가");
        assertThat(comment.getUid()).isEqualTo(1L);
        assertThat(comment.getPid()).isEqualTo(1L);
        assertThat(comment.isStatus()).isEqualTo(false);
    }
//
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

        List<Comment> commentList2 = commentDao.showAll();

        for (int i = 0; i < commentList1.size(); i++) {
            Comment comment1 = commentList1.get(i);
            Comment comment2 = commentList2.get(i);

            assertThat(comment1.getId()).isEqualTo(comment2.getId());
            assertThat(comment1.getContent()).isEqualTo(comment2.getContent());
            assertThat(comment1.getUid()).isEqualTo(comment2.getUid());
            assertThat(comment1.getPid()).isEqualTo(comment2.getPid());
            assertThat(comment1.isStatus()).isEqualTo(comment2.isStatus());
        }
    }
//
    @Test
    @DisplayName("내용을 수정한다")
    void update() {
        Comment comment1 = Comment.builder()
                .id(1L)
                .content("하이루하이루")
                .uid(1L)
                .pid(1L)
                .build();

        int count = commentDao.update(comment1);
        Comment comment2 = commentDao.show(1L);

        assertThat(count).isEqualTo(1);
        assertThat(comment2.getId()).isEqualTo(1L);
        assertThat(comment2.getContent()).isEqualTo("하이루하이루");
        assertThat(comment2.getUid()).isEqualTo(1L);
        assertThat(comment2.getPid()).isEqualTo(1L);
        assertThat(comment2.isStatus()).isEqualTo(true);
    }



    @Test
    @DisplayName("댓글 삭제")
    void delete() {
        Long id = 1L;
        assertThat(commentDao.delete(id)).isEqualTo(1);
        assertThat(commentDao.show(id)).isEqualTo(null);
    }
}
