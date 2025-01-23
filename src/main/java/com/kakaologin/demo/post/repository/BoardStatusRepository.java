package com.kakaologin.demo.post.repository;

import com.kakaologin.demo.post.entity.BoardStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardStatusRepository extends JpaRepository<BoardStatus, Long> {
    BoardStatus findByBoardId(Long postId);
}
