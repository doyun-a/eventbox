package com.kakaologin.demo.service;

import com.kakaologin.demo.dto.KakaoDataDTO;
import com.kakaologin.demo.dto.KakaoUserInfoResponseDto;
import com.kakaologin.demo.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class KakaoUserService {

    public ResponseDTO getUserInfo(KakaoUserInfoResponseDto userInfo, String jwtToken) {
        // DataDTO 생성
        KakaoDataDTO data = new KakaoDataDTO();
        data.setId(userInfo.getId());
        data.setNickname(userInfo.getKakaoAccount().getProfile().getNickName());
        data.setConnectedAt(userInfo.getConnectedAt());
        data.setJwt(jwtToken);

        // ResponseDTO 생성
        ResponseDTO response = new ResponseDTO();
        response.setStatus("success");
        response.setMessage("User info retrieved successfully");
        response.setData(data);

        return response;
    }
}
