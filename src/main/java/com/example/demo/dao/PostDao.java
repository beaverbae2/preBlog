package com.example.demo.dao;

import com.example.demo.dto.User;


public interface UserDao {
    public int save(User user); // 회원 가입
    public User findByUid(String uid); // 회원 정보 확인
    public int delete(String uid); // 회원 탈퇴
}
