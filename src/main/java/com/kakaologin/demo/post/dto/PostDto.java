package com.kakaologin.demo.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;

    private String summary;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int likes;
    private int comments;
    private boolean isBookmarked;
    private int views;

    private User1Dto author;

    private List<String> tagList;
}
