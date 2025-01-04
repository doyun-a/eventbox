package com.kakaologin.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverDataDTO {

    private String id;
    private String nickname;
    private String email;
    private String jwt;

    // 생성자, Getter, Setter
}