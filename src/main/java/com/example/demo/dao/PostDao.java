package com.example.demo.dao;

import com.example.demo.dto.Post;

import java.util.List;

public interface PostDao {

    int write(Post post);
    Post show(Long id);

    List<Post> showAll();

    int update(Post post);

    int updateShows(Long id);

    int delete(Long id);
}
