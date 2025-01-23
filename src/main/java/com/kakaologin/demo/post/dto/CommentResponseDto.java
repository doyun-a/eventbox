package com.kakaologin.demo.post.dto;

import com.kakaologin.demo.post.entity.Comment;
import com.kakaologin.demo.post.entity.Users;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentResponseDto {
    private Long id;

    private LocalDateTime createdAt;

    private String comment_content;

    private Users author;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment_content = comment.getComment_content();
        this.createdAt = comment.getCreatedAt();
        this.author = comment.getAuthor();

    }
}
