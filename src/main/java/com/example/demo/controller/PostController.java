package com.example.demo.controller;


import com.example.demo.dto.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping(value = "/post")
    public int save(@RequestBody Post post) {
        return service.write(post);
    }

    @GetMapping(value = "/post")
    public Post show(@RequestParam Long id){
        return service.show(id);
    }

    @GetMapping(value = "/posts")
    public List<Post> showAll() { return service.showAll(); }

    @PutMapping(value = "/post")
    public int update(@RequestBody Post post) { return service.update(post); }

    @PutMapping(value = "/post/shows")
    public int updateShows(@RequestParam Long id) { return service.updateShows(id); }

    @DeleteMapping(value = "/post")
    public int delete(@RequestParam Long id) { return service.delete(id); }
}