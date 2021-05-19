package com.example.demo.dao;


import com.example.demo.dto.Comment;

import java.util.List;

public interface CommentDao {

    int write(Comment comment);
    Comment show(Long id);


    List<Comment> showAll();

    int update(Comment comment);

    int delete(Long id);
}
