package com.example.demo.dao.impl;

import com.example.demo.dao.PostDao;
import com.example.demo.dto.Post;
import com.example.demo.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao {

    private final PostMapper mapper;

    @Override
    public int write(Post post) {
        return mapper.write(post);
    }

    @Override
    public Post show(Long id) {
        return mapper.show(id);
    }


}
