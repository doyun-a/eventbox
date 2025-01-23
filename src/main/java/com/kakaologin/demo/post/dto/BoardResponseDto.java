package com.kakaologin.demo.post.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class BoardResponseDto {
    private Long id;
    private String title;

    private String summary;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int likes;
    private int commentcount;
    private boolean isBookmarked;
    private int views;
    private String category;

    private User1Dto author;


}
