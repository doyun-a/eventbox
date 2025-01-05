package com.kakaologin.demo.service.Impl;

import com.kakaologin.demo.dto.NaverDTO;

import com.kakaologin.demo.dto.NaverDataDTO;
import com.kakaologin.demo.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class NaverUserService {

    public ResponseDTO getNaverUserInfo(NaverDTO naverUserInfo, String jwtToken) {

        NaverDataDTO data = new NaverDataDTO();
        data.setId(naverUserInfo.getId());
        data.setNickname(naverUserInfo.getName());
        data.setEmail(naverUserInfo.getEmail());
        data.setJwt(jwtToken);

        // ResponseDTO 생성
        ResponseDTO response = new ResponseDTO();
        response.setStatus("success");
        response.setMessage("Naver user info retrieved successfully");
        response.setData(data);

        return response;
    }
}