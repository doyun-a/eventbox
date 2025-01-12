package com.kakaologin.demo.kakaologin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseDTO {

    private String status;
    private String message;
    private Object data; // 데이터는 또 다른 DTO로 관리

    // 생성자, Getter, Setter, toString
}