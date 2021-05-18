package com.example.demo.mapper;

import com.example.demo.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    int write(Post post);
    Post show(Long id);
    List<Post> showAll();
    int update(Post post);

    int updateShows(Long id);

    int delete(Long id);
}
