package com.example.demo.service;


import com.example.demo.dto.Comment;

import java.util.List;

public interface CommentService {

    Comment show(Long id);

    int write(Comment comment);

    List<Comment> showAll();

    int update(Comment comment);

    int delete(Long id);
}
