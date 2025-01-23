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
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likes;
    private int commentcount;
    private boolean isBookmarked;
    private int views;
    private boolean isDeleted;
    private String category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // Board와 연결된 댓글들


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users author;





}


