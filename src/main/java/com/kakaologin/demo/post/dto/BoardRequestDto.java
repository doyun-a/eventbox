package com.kakaologin.demo.post.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BoardRequestDto {

    private Long id;
    private String title;

    private String contents;
    private  String userid;
    private String category;



}
