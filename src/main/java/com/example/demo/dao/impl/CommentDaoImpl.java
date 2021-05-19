package com.example.demo.dao.impl;

import com.example.demo.dao.CommentDao;
import com.example.demo.dto.Comment;
import com.example.demo.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

    private final CommentMapper mapper;


    @Override
    public int write(Comment comment) {
        return mapper.write(comment);
    }

    @Override
    public Comment show(Long id) {
        return mapper.show(id);
    }

    @Override
    public List<Comment> showAll() {
        return  mapper.showAll();
    }

    @Override
    public int update(Comment comment) {
        return mapper.update(comment);
    }

    @Override
    public int delete(Long id) {
        return mapper.delete(id);
    }
}
