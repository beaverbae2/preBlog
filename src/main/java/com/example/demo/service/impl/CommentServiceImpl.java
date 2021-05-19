package com.example.demo.service.impl;


import com.example.demo.dao.CommentDao;
import com.example.demo.dao.PostDao;
import com.example.demo.dto.Comment;
import com.example.demo.dto.Post;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;


    @Override
    public Comment show(Long id) {
        return commentDao.show(id);
    }

    @Override
    public int write(Comment comment) {
        return commentDao.write(comment);
    }

    @Override
    public List<Comment> showAll() {
        return commentDao.showAll();
    }

    @Override
    public int update(Comment comment) {
        return commentDao.update(comment);
    }

    @Override
    public int delete(Long id) {
        return commentDao.delete(id);
    }
}
