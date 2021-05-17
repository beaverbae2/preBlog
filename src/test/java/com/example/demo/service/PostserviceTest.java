package com.example.demo.service;


import com.example.demo.dao.PostDao;
import com.example.demo.dto.Post;
import com.example.demo.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

}