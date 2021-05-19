package com.example.demo.service;


import com.example.demo.dao.CommentDao;
import com.example.demo.dto.Comment;
import com.example.demo.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CommentServiceTest {
    private CommentService commentService;

    @Mock
    private CommentDao commentDao;

    private Comment comment1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentDao);

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

        when(commentDao.write(comment1)).thenReturn(1);

        int count = commentService.write(comment1);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void show() throws Exception {
        //given

        when(commentDao.show(1L)).thenReturn(comment1);

        Comment comment2 = commentService.show(comment1.getId());
        assertThat(comment2.getId()).isEqualTo(comment1.getId());
        assertThat(comment2.getContent()).isEqualTo(comment1.getContent());
        assertThat(comment2.getUid()).isEqualTo(comment1.getUid());
        assertThat(comment2.getPid()).isEqualTo(comment1.getPid());
        assertThat(comment2.isStatus()).isEqualTo(comment1.isStatus());
    }

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

        when(commentDao.showAll()).thenReturn(commentList1);

        List<Comment> commentList2 = commentService.showAll();

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

    @Test
    @DisplayName("내용을 수정한다")
    void update() {
        Comment comment1 = Comment.builder()
                .id(1L)
                .content("하이루하이루")
                .uid(1L)
                .pid(1L)
                .status(true)
                .build();

        when(commentDao.update(comment1)).thenReturn(1);
        when(commentDao.show(1L)).thenReturn(comment1);

        assertThat(commentService.update(comment1)).isEqualTo(1);

        Comment comment2 = commentService.show(1L);

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

        when(commentDao.delete(id)).thenReturn(1);
        when(commentDao.show(id)).thenReturn(null);

        assertThat(commentService.delete(id)).isEqualTo(1);
        assertThat(commentService.show(id)).isEqualTo(null);
    }
}