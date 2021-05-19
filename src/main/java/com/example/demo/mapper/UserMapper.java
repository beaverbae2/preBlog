package com.example.demo.mapper;

import com.example.demo.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public int save(User user); // 회원 가입
    public User findByUid(String uid); // 회원 정보 확인
    public int delete(String uid); // 회원 탈퇴
    public int update(User user);
}
