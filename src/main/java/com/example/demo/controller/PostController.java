package com.example.demo.controller;


import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping(value = "/user")
    public int save(@RequestBody User user) {
        return service.save(user);
    }

    @GetMapping(value = "/user")
    public User findByUid(@RequestParam String uid) { return service.findByUid(uid); }

    @PutMapping(value = "/user")
    public int delete(@RequestParam String uid) { return service.delete(uid); }
}