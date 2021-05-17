package com.example.demo.service.impl;


import com.example.demo.dao.PostDao;
import com.example.demo.dto.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDao postDao;

    @Override
    public int write(Post post) {
        return postDao.write(post);
    }

    @Override
    public Post show(Long id) {
        return postDao.show(id);
    }
}
