package com.kakaologin.demo.post.dto;

import com.kakaologin.demo.post.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardDetailResponseDto {

    private Long id;
    private String title;
    private String contents;
    private List<CommentResponseDto> comments;

    public BoardDetailResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.comments = board.getComments()
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
