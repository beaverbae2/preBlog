package com.example.demo.dao;

import com.example.demo.dto.Post;

public interface PostDao {

    int write(Post post);
    Post show(Long id);
}
