package com.example.demo.dao.impl;

import com.example.demo.dao.PostDao;
import com.example.demo.dto.Post;
import com.example.demo.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Post> showAll() {
        return mapper.showAll();
    }

    @Override
    public int update(Post post) {
        return mapper.update(post);
    }

    @Override
    public int updateShows(Long id) {
        return mapper.updateShows(id);
    }

    @Override
    public int delete(Long id) {
        return mapper.delete(id);
    }


}
