package com.example.demo.service;


import com.example.demo.dao.PostDao;
import com.example.demo.dto.Post;
import com.example.demo.service.impl.PostServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PostserviceTest {
    private PostService postService;

    @Mock
    private PostDao postDao;

    private Post post1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostServiceImpl(postDao);

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

        when(postDao.write(post1)).thenReturn(1);

        int count = postService.write(post1);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void show() throws Exception {
        //given
        Post post1 =  Post.builder()
                     .id(1L)
                     .title("하이")
                     .content("하이루")
                     .uid(1L)
                     .shows(1)
                     .status(true)
                     .build();
        when(postDao.show(1L)).thenReturn(post1);

        Post post2 = postService.show(post1.getId());
        assertThat(post2.getId()).isEqualTo(post1.getId());
        assertThat(post2.getUid()).isEqualTo(post1.getUid());
        assertThat(post2.getTitle()).isEqualTo(post1.getTitle());
        assertThat(post2.getContent()).isEqualTo(post1.getContent());
        assertThat(post2.getShows()).isEqualTo(post1.getShows());
        assertThat(post2.isStatus()).isEqualTo(post1.isStatus());
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

        when(postDao.showAll()).thenReturn(postList1);

        List<Post> postList2 = postService.showAll();

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

        when(postDao.update(post1)).thenReturn(1);
        when(postDao.show(1L)).thenReturn(post1);

        assertThat(postService.update(post1)).isEqualTo(1);

        Post post2 = postService.show(1L);
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
                .shows(1+1)
                .build();

        when(postDao.updateShows(post1.getId())).thenReturn(1);
        when(postDao.show(post1.getId())).thenReturn(post1);

        assertThat(postService.updateShows(post1.getId())).isEqualTo(1);
        assertThat(postService.show(post1.getId()).getShows()).isEqualTo(post1.getShows());
    }

    @Test
    @DisplayName("글 삭제")
    void delete() {
        Long id = 1L;

        when(postDao.delete(id)).thenReturn(1);
        when(postDao.show(id)).thenReturn(null);

        assertThat(postService.delete(id)).isEqualTo(1);
        assertThat(postService.show(id)).isEqualTo(null);
    }
}