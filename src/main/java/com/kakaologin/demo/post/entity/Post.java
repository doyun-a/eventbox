package com.kakaologin.demo.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likes;
    private int comments;
    private boolean isBookmarked;
    private int views;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private User1 author;

    @ElementCollection
    private List<String> tagList = new ArrayList<>();



}


