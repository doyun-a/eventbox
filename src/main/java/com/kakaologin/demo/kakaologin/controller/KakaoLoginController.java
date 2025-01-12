package com.kakaologin.demo.kakaologin.controller;

import com.kakaologin.demo.kakaologin.dto.KakaoUserInfoResponseDto;
import com.kakaologin.demo.kakaologin.dto.ResponseDTO;
import com.kakaologin.demo.kakaologin.entity.User;
import com.kakaologin.demo.kakaologin.service.impl.KakaoService;
import com.kakaologin.demo.kakaologin.service.impl.KakaoUserService;
import com.kakaologin.demo.kakaologin.repository.UserRepository;
import com.kakaologin.demo.kakaologin.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KakaoLoginController {
git a
    private final KakaoService kakaoService;

    private final UserRepository userRepository;

    private final KakaoUserService kakaoUserService;

    private final JwtService jwtService;

    @GetMapping("/a")
    public String test(){
        return "test";
    }

    @Transactional
    @PostMapping("/callback")
    public ResponseEntity<ResponseDTO> callback(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        String code = requestBody.get("code");
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


        ResponseCookie cookie = ResponseCookie.from("jwtToken", jwtToken)
                .httpOnly(false) // JavaScript에서 접근 불가
                .secure(false)   // HTTPS에서만 전송
                .path("/")      // 쿠키가 유효한 경로
                .sameSite("Lax") // CSRF 공격 방어
                .domain("localhost")
                .build();

        response.setHeader("Set-Cookie", cookie.toString());


        ResponseDTO responseDTO = kakaoUserService.getUserInfo(userInfo, jwtToken);
        return ResponseEntity.ok(responseDTO);
    }

}
