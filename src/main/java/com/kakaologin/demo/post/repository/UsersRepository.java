package com.kakaologin.demo.post.repository;


import com.kakaologin.demo.post.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUserid(String userid);
}
