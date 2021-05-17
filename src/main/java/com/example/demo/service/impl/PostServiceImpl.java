package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao dao;

    @Override
    public int save(User user) {
        return dao.save(user);
    }

    @Override
    public User findByUid(String uid) {
        return dao.findByUid(uid);
    }

    @Override
    public int delete(String uid) {
        return dao.delete(uid);
    }
}
