package com.kakaologin.demo.service;


import com.kakaologin.demo.dto.KakaoUserInfoResponseDto;
import com.kakaologin.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByOauthId(String oauthId);
}
