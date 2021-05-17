package com.example.demo.dao.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.User;
import com.example.demo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final UserMapper mapper;

    @Override
    public int save(User user) {
        return mapper.save(user);
    }

    @Override
    public User findByUid(String uid) {
        return mapper.findByUid(uid);
    }

    @Override
    public int delete(String uid) {
        return mapper.delete(uid);
    }
}
