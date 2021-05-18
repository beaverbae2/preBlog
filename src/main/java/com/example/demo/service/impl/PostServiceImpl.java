package com.example.demo.service.impl;


import com.example.demo.dao.PostDao;
import com.example.demo.dto.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Post> showAll() {
        return postDao.showAll();
    }

    @Override
    public int update(Post post) {
        return postDao.update(post);
    }

    @Override
    public int updateShows(Long id) {
        return postDao.updateShows(id);
    }

    @Override
    public int delete(Long id) {
        return postDao.delete(id);
    }
}
