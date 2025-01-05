package com.kakaologin.demo.controller;

import com.kakaologin.demo.service.Impl.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KakaoLoginPageController {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    private final NaverService naverService;
    @GetMapping("/page")
    public String loginPage(Model model) {

        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);
        model.addAttribute("naverUrl", naverService.getNaverLogin());
        return "login";
    }




}
