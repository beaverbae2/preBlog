package com.example.demo.service;


import com.example.demo.dto.Post;

public interface PostService {

    int write(Post post);

    Post show(Long id);
}
