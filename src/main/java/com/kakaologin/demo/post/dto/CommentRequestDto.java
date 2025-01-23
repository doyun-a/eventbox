package com.kakaologin.demo.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CommentRequestDto {

    private Long id;

    private String comment_content;

    private String userid;


}
