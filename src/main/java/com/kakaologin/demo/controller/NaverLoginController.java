package com.kakaologin.demo.controller;

import com.kakaologin.demo.dto.NaverDTO;
import com.kakaologin.demo.dto.ResponseDTO;
import com.kakaologin.demo.entity.User;
import com.kakaologin.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/naverLogin")
public class NaverLoginController {

    private final NaverService naverService;

    private final  NaverUserService naverUserService;

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ResponseDTO> callback(@RequestParam("code") String code) throws Exception {
        NaverDTO naverInfo = naverService.getNaverInfo(code);
        String jwtToken = naverService.createJwtForUser(naverInfo);

        Optional<User> existingUser = userRepository.findByOauthId(naverInfo.getId());

        User naveruser;
        if (existingUser.isPresent()) {
            // 기존 사용자 정보가 있으면 업데이트
            naveruser = existingUser.get();
            naveruser.setNickname(naverInfo.getName());
            naveruser.setOauthId(naverInfo.getId());
            naveruser.setConnectedAt(new Date());
            naveruser.setEmail(naverInfo.getEmail());
            naveruser.setWebName("N");

        } else {
            // 새로 사용자 등록
            naveruser = new User();
            naveruser.setOauthId(naverInfo.getId());
            naveruser.setNickname(naverInfo.getName());
            naveruser.setConnectedAt(new Date());
            naveruser.setEmail(naverInfo.getEmail());
            naveruser.setWebName("N");
        }
        userRepository.save(naveruser);

        ResponseDTO response = naverUserService.getNaverUserInfo(naverInfo,jwtToken);
        return ResponseEntity.ok(response);
    }

}
