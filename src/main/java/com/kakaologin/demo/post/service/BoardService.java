package com.kakaologin.demo.post.service;

import com.kakaologin.demo.post.dto.*;
import com.kakaologin.demo.post.entity.Comment;
import com.kakaologin.demo.post.repository.CommentRepository;

import java.util.List;

public interface BoardService {

     int getView(Long postId);

    void incrementView(Long postId);

    void createBoard(BoardRequestDto boardRequestDto);

    List<BoardResponseDto> getAllBoards(int page);

    boolean deleteBoard(Long id);

    void addComment(Long boardId, CommentRequestDto commentRequestDto);

    List<CommentResponseDto> getCommentsByBoardId(Long boardId);

    BoardDetailResponseDto getBoardDetails(Long boardId);

}
