package com.kakaologin.demo.controller;

import com.kakaologin.demo.dto.KakaoUserInfoResponseDto;
import com.kakaologin.demo.dto.ResponseDTO;
import com.kakaologin.demo.entity.User;
import com.kakaologin.demo.service.KakaoService;
import com.kakaologin.demo.service.UserRepository;
import com.kakaologin.demo.service.KakaoUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    private final UserRepository userRepository;

    private final KakaoUserService kakaoUserService;

    @Transactional
    @PostMapping("/callback")
    public ResponseEntity<ResponseDTO> callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        Optional<User> existingUser = userRepository.findByOauthId(userInfo.getId());

        User user;
        if (existingUser.isPresent()) {
            // 기존 사용자 정보가 있으면 업데이트
            user = existingUser.get();
            user.setNickname(userInfo.getKakaoAccount().getProfile().getNickName());
            user.setOauthId(userInfo.getId());
            user.setConnectedAt(new Date());
            user.setWebName("K");

        } else {
            // 새로 사용자 등록
            user = new User();
            user.setOauthId(userInfo.getId());
            user.setNickname(userInfo.getKakaoAccount().getProfile().getNickName());
            user.setConnectedAt(new Date());
            user.setWebName("K");

        }
        userRepository.save(user);

        // User 로그인, 또는 회원가입 로직 추가
        String jwtToken = kakaoService.createJwtForUser(userInfo);
        ResponseDTO response = kakaoUserService.getUserInfo(userInfo, jwtToken);
        return ResponseEntity.ok(response);
    }
}
