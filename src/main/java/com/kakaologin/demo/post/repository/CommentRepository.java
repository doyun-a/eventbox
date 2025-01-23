package com.kakaologin.demo.post.repository;

import com.kakaologin.demo.post.dto.CommentResponseDto;
import com.kakaologin.demo.post.entity.Board;
import com.kakaologin.demo.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.author WHERE c.board.id = :boardId")
    List<Comment> findCommentsByBoardIdWithAuthor(@Param("boardId") Long boardId);
}
