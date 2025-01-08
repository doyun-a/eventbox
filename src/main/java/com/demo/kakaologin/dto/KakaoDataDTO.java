package com.kakaologin.demo.kakaologin.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class KakaoDataDTO {

    private String id;
    private Date connectedAt;
    private String nickname;
    private String jwt;

    // 생성자, Getter, Setter, toString
}
