package com.example.demo.mapper;

import com.example.demo.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
   int write(Comment comment);

    Comment show(Long id);

    List<Comment> showAll();

    int update(Comment comment);

    int delete(Long id);
}
