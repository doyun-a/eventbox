package com.kakaologin.demo.post.controller;

import com.kakaologin.demo.post.dto.*;
import com.kakaologin.demo.post.entity.Comment;
import com.kakaologin.demo.post.repository.CommentRepository;
import com.kakaologin.demo.post.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService)
    {
        this.boardService = boardService;

    }

    // 게시글 작성 API
    @PostMapping
    public ResponseEntity<Void> createBoard(@RequestBody BoardRequestDto boardrequestDto) {
        boardService.createBoard(boardrequestDto);
        return ResponseEntity.ok().build();
    }

    //게시글 목록
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllPosts(
            @RequestParam("page") int page)
    {
        List<BoardResponseDto> posts = boardService.getAllBoards(page);
        return ResponseEntity.ok(posts);
    }


    //게시글 삭제
    @PostMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        boolean isDeleted = boardService.deleteBoard(id);
        if (isDeleted) {
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        }
    }

    @GetMapping("/{boardId}/view")
    public int getViewCount(@PathVariable("boardId") Long boardId) {
        System.out.print("-----------------success---------");
        return boardService.getView(boardId);  // 조회수 반환
    }
    @PostMapping("/{boardId}/increase-view")
    public ResponseEntity<Void> increaseViewCount(@PathVariable("boardId") Long boardId) {
        boardService.incrementView(boardId);  // 조회수 증가
        return ResponseEntity.ok().build();  // 조회수 증가 성공
    }



    @PostMapping("/{boardId}/comments")
    public ResponseEntity<Void> addComment(@PathVariable("boardId") Long boardId,
                                           @RequestBody CommentRequestDto commentRequestDto)
    {
        boardService.addComment(boardId,commentRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}/comments")
    public List<CommentResponseDto> getCommentsByBoardId(@PathVariable("boardId") Long boardId) {
        return boardService.getCommentsByBoardId(boardId);
    }

    @GetMapping("/{boardId}/detail")
    public ResponseEntity<BoardDetailResponseDto> getBoard(@PathVariable("boardId") Long boardId) {
        BoardDetailResponseDto response = boardService.getBoardDetails(boardId);
        return ResponseEntity.ok(response);
    }

}

