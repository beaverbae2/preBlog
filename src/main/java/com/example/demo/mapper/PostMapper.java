package com.example.demo.mapper;

import com.example.demo.dto.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    int write(Post post);
    Post show(Long id);
}
