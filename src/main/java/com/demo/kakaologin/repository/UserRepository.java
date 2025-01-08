package com.kakaologin.demo.kakaologin.repository;


import com.kakaologin.demo.kakaologin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByOauthId(String oauthId);
}
