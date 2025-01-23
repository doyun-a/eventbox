package com.kakaologin.demo.post.repository;

import com.kakaologin.demo.post.entity.Board;
import com.kakaologin.demo.post.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /*@Query(value = "SELECT DISTINCT b FROM Board b " +
            "LEFT JOIN FETCH b.author " +
            "WHERE b.isDeleted = false",
            countQuery = "SELECT COUNT(b) FROM Board b WHERE b.isDeleted = false")
    Page<Board> findAllActiveBoards(Pageable pageable);*/


    @Query("SELECT b FROM Board b JOIN FETCH b.author WHERE b.category = :category AND b.isDeleted = false")
    Page<Board> findByCategoryAndIsDeletedFalse(@Param("category") String category, Pageable pageable);



    @Query("SELECT b FROM Board b " +
            "LEFT JOIN FETCH b.comments c " +
            "LEFT JOIN FETCH c.author " +
            "WHERE b.id = :boardId")
    Board findByIdWithCommentsAndAuthor(@Param("boardId") Long boardId);


}
