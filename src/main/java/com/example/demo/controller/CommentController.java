package com.example.demo.controller;


import com.example.demo.dto.Comment;
import com.example.demo.dto.Post;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping(value = "/comment")
    public int write(@RequestBody Comment comment) {
        return service.write(comment);
    }

    @GetMapping(value = "/comment")
    public Comment show(@RequestParam Long id){ return service.show(id);}

    @GetMapping(value = "/comments")
    public List<Comment> showall(){return service.showAll();}

    @PutMapping(value= "/comment")
    public int update(@RequestBody Comment comment){return service.update(comment);}

    @DeleteMapping(value = "/comment")
    public int delete(@RequestParam Long id) {return service.delete(id);}
}